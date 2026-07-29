package com.anuppur.security;

import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.anuppur.bean.WorkBean;
import com.anuppur.entity.DepartmentRemarks;
import com.anuppur.entity.DmRemarks;
import com.anuppur.entity.Users;
import com.anuppur.entity.Work;
import com.anuppur.repository.DepartmentRemarksRepository;
import com.anuppur.repository.DmRemarksRepository;
import com.anuppur.repository.UserRepository;
import com.anuppur.repository.WorkRepository;
import com.anuppur.util.DMSUtil;

/**
 * Object-level authorization used by method-security expressions.
 *
 * <p>Role checks alone are not enough for work mutations: a department or DM
 * must also be scoped to the work being changed. All ambiguous/missing scope
 * data is denied instead of silently granting cross-district access.</p>
 */
@Component("workAuthorization")
public class WorkAuthorization {

    private static final String ROLE_SYSTEM_ADMIN = "ROLE_SYSTEM_ADMIN";
    private static final String ROLE_DM = "ROLE_DM";
    private static final String ROLE_DEPARTMENT = "ROLE_DEPARTMENT";

    private final UserRepository userRepository;
    private final WorkRepository workRepository;
    private final DmRemarksRepository dmRemarksRepository;
    private final DepartmentRemarksRepository departmentRemarksRepository;

    public WorkAuthorization(UserRepository userRepository, WorkRepository workRepository,
            DmRemarksRepository dmRemarksRepository,
            DepartmentRemarksRepository departmentRemarksRepository) {
        this.userRepository = userRepository;
        this.workRepository = workRepository;
        this.dmRemarksRepository = dmRemarksRepository;
        this.departmentRemarksRepository = departmentRemarksRepository;
    }

    public boolean canAccessWork(Long workId) {
        Authentication authentication = authentication();
        if (!isAuthenticated(authentication) || workId == null) {
            return false;
        }
        if (hasAuthority(authentication, ROLE_SYSTEM_ADMIN)) {
            return true;
        }

        Users currentUser = userRepository.findByUsername(authentication.getName());
        Work work = workRepository.findById(workId).orElse(null);
        return currentUser != null && work != null && isWithinScope(authentication, currentUser, work);
    }

    public boolean canAccessEncryptedWork(String encryptedWorkId) {
        try {
            return canAccessWork(Long.valueOf(DMSUtil.decryptParam(encryptedWorkId)));
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public boolean canEditWork(WorkBean bean) {
        Authentication authentication = authentication();
        if (!isAuthenticated(authentication) || bean == null) {
            return false;
        }

        boolean changesDmDecision = bean.getDmStatus() != null
                || StringUtils.hasText(bean.getDmRemakrs());
        if (changesDmDecision && !hasAuthority(authentication, ROLE_DM)) {
            return false;
        }
        if (hasAuthority(authentication, ROLE_SYSTEM_ADMIN) && !changesDmDecision) {
            return true;
        }

        Users currentUser = userRepository.findByUsername(authentication.getName());
        if (currentUser == null) {
            return false;
        }

        if (bean.getId() != null) {
            Work existing = workRepository.findById(bean.getId()).orElse(null);
            return existing != null
                    && isWithinScope(authentication, currentUser, existing)
                    && requestedScopeMatches(authentication, currentUser, bean, false);
        }
        return requestedScopeMatches(authentication, currentUser, bean, true);
    }

    public boolean canAssignUserToWork(Long userId, Long workId) {
        Authentication authentication = authentication();
        if (!isAuthenticated(authentication) || userId == null || workId == null) {
            return false;
        }
        if (hasAuthority(authentication, ROLE_SYSTEM_ADMIN)) {
            return true;
        }
        if (!hasAuthority(authentication, ROLE_DM) || !canAccessWork(workId)) {
            return false;
        }

        Users assignee = userRepository.findById(userId).orElse(null);
        Work work = workRepository.findById(workId).orElse(null);
        if (assignee == null || work == null || assignee.getDistrict() == null) {
            return false;
        }
        return sameDistrict(assignee, work);
    }

    public boolean canDeleteDmRemark(Long remarkId) {
        DmRemarks remark = remarkId == null ? null : dmRemarksRepository.findById(remarkId).orElse(null);
        return remark != null && canAccessWork(remark.getWorkId());
    }

    public boolean canDeleteDepartmentRemark(Long remarkId) {
        DepartmentRemarks remark = remarkId == null
                ? null
                : departmentRemarksRepository.findById(remarkId).orElse(null);
        return remark != null && canAccessWork(remark.getWorkId());
    }

    private boolean isWithinScope(Authentication authentication, Users user, Work work) {
        if (hasAuthority(authentication, ROLE_DM)) {
            return user.getDistrict() != null && sameDistrict(user, work);
        }
        if (hasAuthority(authentication, ROLE_DEPARTMENT)) {
            boolean hasScope = false;
            if (user.getImplementationAgency() != null) {
                hasScope = true;
                if (!Objects.equals(user.getImplementationAgency().getImplementationAgencyId(),
                        work.getImplementationAgency())) {
                    return false;
                }
            }
            if (user.getDivision() != null) {
                hasScope = true;
                Long divisionId = user.getDivision().getDivisionId();
                if (!Objects.equals(divisionId, work.getDivisionId())
                        && !Objects.equals(divisionId, work.getDivisionCode())) {
                    return false;
                }
            }
            if (user.getDistrict() != null) {
                hasScope = true;
                if (!sameDistrict(user, work)) {
                    return false;
                }
            }
            return hasScope;
        }
        return false;
    }

    private boolean requestedScopeMatches(Authentication authentication, Users user, WorkBean bean,
            boolean requireRequestedScope) {
        if (hasAuthority(authentication, ROLE_DM)) {
            return user.getDistrict() != null
                    && (requestedDistrictMatches(user, bean)
                            || (!requireRequestedScope && !hasRequestedDistrict(bean)));
        }
        if (!hasAuthority(authentication, ROLE_DEPARTMENT)) {
            return false;
        }

        boolean hasScope = false;
        if (user.getImplementationAgency() != null) {
            if (bean.getImplementationAgency() != null) {
                hasScope = true;
            }
            if (bean.getImplementationAgency() != null
                    && !Objects.equals(user.getImplementationAgency().getImplementationAgencyId(),
                            bean.getImplementationAgency())) {
                return false;
            }
        }
        if (user.getDivision() != null) {
            if (bean.getDivisionId() != null) {
                hasScope = true;
            }
            if (bean.getDivisionId() != null
                    && !Objects.equals(user.getDivision().getDivisionId(), bean.getDivisionId())) {
                return false;
            }
        }
        if (user.getDistrict() != null) {
            if (hasRequestedDistrict(bean)) {
                hasScope = true;
            }
            if (hasRequestedDistrict(bean) && !requestedDistrictMatches(user, bean)) {
                return false;
            }
        }
        return hasScope || !requireRequestedScope;
    }

    private boolean requestedDistrictMatches(Users user, WorkBean bean) {
        Long userDistrictId = user.getDistrict().getDistrictId();
        String userDistrictCode = user.getDistrict().getDistrictCode();
        boolean idMatches = bean.getDistrictId() != null && Objects.equals(userDistrictId, bean.getDistrictId());
        boolean codeMatches = StringUtils.hasText(bean.getDistrictCode())
                && Objects.equals(userDistrictCode, bean.getDistrictCode());
        return idMatches || codeMatches;
    }

    private boolean hasRequestedDistrict(WorkBean bean) {
        return bean.getDistrictId() != null || StringUtils.hasText(bean.getDistrictCode());
    }

    private boolean sameDistrict(Users user, Work work) {
        return Objects.equals(user.getDistrict().getDistrictId(), work.getDistrictId())
                || Objects.equals(user.getDistrict().getDistrictCode(), work.getDistrictCode());
    }

    private Authentication authentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private boolean isAuthenticated(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal());
    }

    private boolean hasAuthority(Authentication authentication, String authority) {
        return authentication.getAuthorities().stream()
                .anyMatch(granted -> authority.equals(granted.getAuthority()));
    }
}
