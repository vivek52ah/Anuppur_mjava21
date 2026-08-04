package com.anuppur.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.anuppur.bean.RoleBean;
import com.anuppur.bean.UserBean;
import com.anuppur.constants.DMSConstants;
import com.anuppur.entity.District;
import com.anuppur.entity.Role;
import com.anuppur.entity.Users;
import com.anuppur.repository.UserRepository;

class UserAuthorizationTest {

    private final UserAuthorization authorization = new UserAuthorization();

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void acceptsOnlyAreaOfficerRequest() {
        UserBean areaOfficer = new UserBean();
        areaOfficer.setDesignationId(1L);
        areaOfficer.setRole(new RoleBean("ROLE_AREA_OFFICER", "Area Officer"));

        UserBean department = new UserBean();
        department.setDesignationId(2L);
        department.setRole(new RoleBean("ROLE_DEPARTMENT", "Department"));

        assertThat(authorization.isAreaOfficerRequest(areaOfficer)).isTrue();
        assertThat(authorization.isAreaOfficerRequest(department)).isFalse();
        assertThat(authorization.isAreaOfficerRequest(null)).isFalse();
    }

    @Test
    void rejectsRoleSpoofingEvenWithAreaOfficerDesignation() {
        UserBean spoofed = new UserBean();
        spoofed.setDesignationId(1L);
        spoofed.setRole(new RoleBean("ROLE_SYSTEM_ADMIN", "System Admin"));

        assertThat(authorization.isAreaOfficerRequest(spoofed)).isFalse();
    }

    @Test
    void departmentCanManageOnlyAreaOfficersInItsDepartmentAndDistrict() {
        UserRepository users = mock(UserRepository.class);
        UserAuthorization scopedAuthorization = new UserAuthorization(users);
        UserDetails principal = User.withUsername("department@example.com")
                .password("unused").roles("DEPARTMENT").build();
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities()));

        Users department = departmentUser("Roads", 7L);
        when(users.findByUsernameAndStatus("department@example.com", DMSConstants.STATUS_ACTIVE))
                .thenReturn(department);

        Users ownedAreaOfficer = areaOfficer("Roads", 7L);
        when(users.findById(10L)).thenReturn(Optional.of(ownedAreaOfficer));
        assertThat(scopedAuthorization.canManageAreaOfficer(10L)).isTrue();

        Users otherDepartment = areaOfficer("Forest", 7L);
        when(users.findById(11L)).thenReturn(Optional.of(otherDepartment));
        assertThat(scopedAuthorization.canManageAreaOfficer(11L)).isFalse();

        Users privilegedUser = departmentUser("Roads", 7L);
        when(users.findById(12L)).thenReturn(Optional.of(privilegedUser));
        assertThat(scopedAuthorization.canManageAreaOfficer(12L)).isFalse();
    }

    private Users areaOfficer(String departmentName, Long districtId) {
        Users user = new Users();
        user.setDesignationID(1L);
        user.setDepartmentName(departmentName);
        user.setDistrict(new District(districtId));
        user.setRoles(Set.of(new Role("ROLE_AREA_OFFICER")));
        return user;
    }

    private Users departmentUser(String departmentName, Long districtId) {
        Users user = new Users();
        user.setDesignationID(2L);
        user.setDepartmentName(departmentName);
        user.setDistrict(new District(districtId));
        user.setRoles(Set.of(new Role("ROLE_DEPARTMENT")));
        return user;
    }
}
