package com.anuppur.security;

import org.springframework.stereotype.Component;

import com.anuppur.bean.UserBean;

/** Object-level authorization rules for delegated user administration. */
@Component("userAuthorization")
public class UserAuthorization {

    private static final long AREA_OFFICER_DESIGNATION_ID = 1L;
    private static final String AREA_OFFICER_ROLE = "ROLE_AREA_OFFICER";

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
}
