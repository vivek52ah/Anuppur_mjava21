package com.anuppur.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.anuppur.bean.RoleBean;
import com.anuppur.bean.UserBean;

class UserAuthorizationTest {

    private final UserAuthorization authorization = new UserAuthorization();

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
}
