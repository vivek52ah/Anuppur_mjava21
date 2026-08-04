package com.anuppur.security;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.anuppur.bean.UserBean;
import com.anuppur.constants.DMSConstants;
import com.anuppur.entity.Users;
import com.anuppur.repository.UserRepository;
import com.anuppur.util.DMSUtil;

/** Object-level authorization rules for delegated user administration. */
@Component("userAuthorization")
public class UserAuthorization {

    private static final long AREA_OFFICER_DESIGNATION_ID = 1L;
    private static final String AREA_OFFICER_ROLE = "ROLE_AREA_OFFICER";

    private UserRepository userRepository;

    public UserAuthorization() {
        // Used by focused request-shape tests that do not access stored users.
    }

    @Autowired
    public UserAuthorization(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Department users may create Area Officers only. The service also replaces
     * all submitted area fields with the logged-in department user's scope.
     */
    public boolean isAreaOfficerRequest(UserBean bean) {
        if (bean == null || bean.getDesignationId() == null
                || bean.getDesignationId() != AREA_OFFICER_DESIGNATION_ID) {
            return false;
        }

        return bean.getRole() == null
                || bean.getRole().getRoleCode() == null
                || AREA_OFFICER_ROLE.equals(bean.getRole().getRoleCode());
    }

    public boolean canManageEncryptedAreaOfficer(String encryptedId) {
        try {
            return canManageAreaOfficer(Long.valueOf(DMSUtil.decryptParam(encryptedId)));
        } catch (RuntimeException exception) {
            return false;
        }
    }

    public boolean canManageAreaOfficerRequest(UserBean bean) {
        return bean != null && bean.getId() != null
                && isAreaOfficerRequest(bean)
                && canManageAreaOfficer(bean.getId());
    }

    public boolean canManageAreaOfficer(Long userId) {
        if (userRepository == null || userId == null) {
            return false;
        }

        User principal = DMSUtil.getUserDetail();
        if (principal == null) {
            return false;
        }
        Users loggedInUser = userRepository.findByUsernameAndStatus(
                principal.getUsername(), DMSConstants.STATUS_ACTIVE);
        Users target = userRepository.findById(userId).orElse(null);
        if (loggedInUser == null || target == null
                || target.getDesignationID() == null
                || target.getDesignationID() != AREA_OFFICER_DESIGNATION_ID
                || target.getRoles() == null
                || target.getRoles().stream().noneMatch(role -> AREA_OFFICER_ROLE.equals(role.getRoleCode()))) {
            return false;
        }

        String department = loggedInUser.getDepartmentName();
        if (department == null || department.isBlank()
                || !Objects.equals(department, target.getDepartmentName())) {
            return false;
        }

        if (loggedInUser.getDistrict() == null) {
            return true;
        }
        return target.getDistrict() != null
                && Objects.equals(loggedInUser.getDistrict().getDistrictId(),
                        target.getDistrict().getDistrictId());
    }
}
