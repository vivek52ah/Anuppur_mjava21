package com.anuppur.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.anuppur.bean.WorkBean;
import com.anuppur.entity.District;
import com.anuppur.entity.Division;
import com.anuppur.entity.ImplementationAgency;
import com.anuppur.entity.Users;
import com.anuppur.entity.Work;
import com.anuppur.repository.DepartmentRemarksRepository;
import com.anuppur.repository.DmRemarksRepository;
import com.anuppur.repository.UserRepository;
import com.anuppur.repository.WorkRepository;

class WorkAuthorizationTest {

    private UserRepository userRepository;
    private WorkRepository workRepository;
    private WorkAuthorization authorization;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        workRepository = mock(WorkRepository.class);
        authorization = new WorkAuthorization(userRepository, workRepository,
                mock(DmRemarksRepository.class), mock(DepartmentRemarksRepository.class));
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void departmentCanAccessOnlyMatchingAgencyDivisionAndDistrict() {
        Users department = scopedUser(10L, 20L, 30L, "D30");
        Work ownWork = scopedWork(100L, 10L, 20L, 30L, "D30");
        authenticate("department", "ROLE_DEPARTMENT");
        when(userRepository.findByUsername("department")).thenReturn(department);
        when(workRepository.findById(100L)).thenReturn(Optional.of(ownWork));

        assertThat(authorization.canAccessWork(100L)).isTrue();

        ownWork.setImplementationAgency(11L);
        assertThat(authorization.canAccessWork(100L)).isFalse();
    }

    @Test
    void dmCannotAccessAnotherDistrict() {
        Users dm = scopedUser(null, null, 30L, "D30");
        Work otherDistrict = scopedWork(101L, null, null, 31L, "D31");
        authenticate("dm", "ROLE_DM");
        when(userRepository.findByUsername("dm")).thenReturn(dm);
        when(workRepository.findById(101L)).thenReturn(Optional.of(otherDistrict));

        assertThat(authorization.canAccessWork(101L)).isFalse();
    }

    @Test
    void departmentCannotSubmitDmDecisionEvenForOwnWork() {
        Users department = scopedUser(10L, 20L, 30L, "D30");
        Work ownWork = scopedWork(100L, 10L, 20L, 30L, "D30");
        WorkBean request = new WorkBean();
        request.setId(100L);
        request.setDmStatus(1L);

        authenticate("department", "ROLE_DEPARTMENT");
        when(userRepository.findByUsername("department")).thenReturn(department);
        when(workRepository.findById(100L)).thenReturn(Optional.of(ownWork));

        assertThat(authorization.canEditWork(request)).isFalse();
    }

    @Test
    void systemAdminCanAccessAnyExistingWork() {
        authenticate("admin", "ROLE_SYSTEM_ADMIN");
        assertThat(authorization.canAccessWork(999L)).isTrue();
    }

    @Test
    void editPageAuthorizationAcceptsPlainAndLegacyEncodedWorkIds() {
        Users department = scopedUser(null, 20L, 30L, "D30");
        Work ownWork = scopedWork(100L, 10L, 20L, 30L, "D30");
        authenticate("department", "ROLE_DEPARTMENT");
        when(userRepository.findByUsername("department")).thenReturn(department);
        when(workRepository.findById(100L)).thenReturn(Optional.of(ownWork));

        String encoded = Base64.getUrlEncoder().withoutPadding()
                .encodeToString("100".getBytes(StandardCharsets.UTF_8));

        assertThat(authorization.canAccessEncryptedWork("100")).isTrue();
        assertThat(authorization.canAccessEncryptedWork(encoded)).isTrue();
        assertThat(authorization.canAccessEncryptedWork("not-an-id")).isFalse();
    }

    private void authenticate(String username, String authority) {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(username, "n/a",
                        java.util.List.of(new SimpleGrantedAuthority(authority))));
    }

    private Users scopedUser(Long agencyId, Long divisionId, Long districtId, String districtCode) {
        Users user = new Users();
        if (agencyId != null) {
            user.setImplementationAgency(new ImplementationAgency(agencyId));
        }
        if (divisionId != null) {
            Division division = new Division();
            division.setDivisionId(divisionId);
            user.setDivision(division);
        }
        District district = new District();
        district.setDistrictId(districtId);
        district.setDistrictCode(districtCode);
        user.setDistrict(district);
        return user;
    }

    private Work scopedWork(Long id, Long agencyId, Long divisionId, Long districtId, String districtCode) {
        Work work = new Work();
        work.setId(id);
        work.setImplementationAgency(agencyId);
        work.setDivisionId(divisionId);
        work.setDivisionCode(divisionId);
        work.setDistrictId(districtId);
        work.setDistrictCode(districtCode);
        return work;
    }
}
