package com.anuppur.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anuppur.bean.TSASReviseWorkBean;
import com.anuppur.bean.TSASWorkBean;
import com.anuppur.bean.UserBean;
import com.anuppur.controller.CommonController;
import com.anuppur.controller.SuperAdminController;
import com.anuppur.controller.SystemAdminController;

import jakarta.servlet.http.HttpServletRequest;

class AuthorizationContractTest {

    @Test
    void administrativeControllersDefaultToSystemAdmin() {
        assertThat(SystemAdminController.class.getAnnotation(PreAuthorize.class).value())
                .isEqualTo("hasRole('ROLE_SYSTEM_ADMIN')");
        assertThat(SuperAdminController.class.getAnnotation(PreAuthorize.class).value())
                .isEqualTo("hasRole('ROLE_SYSTEM_ADMIN')");
    }

    @Test
    void departmentUserAdministrationStaysRestrictedToAreaOfficersInScope() throws Exception {
        PreAuthorize addUser = SystemAdminController.class.getDeclaredMethod("addUser", UserBean.class,
                HttpServletRequest.class).getAnnotation(PreAuthorize.class);
        assertThat(addUser.value())
                .contains("ROLE_SYSTEM_ADMIN", "ROLE_DM", "ROLE_DEPARTMENT")
                .contains("@userAuthorization.isAreaOfficerRequest(#p0)");

        assertThat(SystemAdminController.class.getDeclaredMethod("editUser", UserBean.class,
                HttpServletRequest.class).getAnnotation(PreAuthorize.class).value())
                .contains("ROLE_DEPARTMENT", "@userAuthorization.canManageAreaOfficerRequest(#p0)");
        assertThat(SystemAdminController.class.getDeclaredMethod("deleteUser", Long.class,
                HttpServletRequest.class).getAnnotation(PreAuthorize.class).value())
                .contains("ROLE_DEPARTMENT", "@userAuthorization.canManageAreaOfficer(#p0)");
    }

    @Test
    void dmStatusMutationsRequireDmAndObjectScope() throws Exception {
        assertDmWorkGuard(CommonController.class.getDeclaredMethod("addTSASWorkDataStatus",
                TSASWorkBean.class, HttpServletRequest.class));
        assertDmWorkGuard(CommonController.class.getDeclaredMethod("addTSReviseWorkDataStatus",
                TSASReviseWorkBean.class, HttpServletRequest.class));
        assertDmWorkGuard(CommonController.class.getDeclaredMethod("addASReviseWorkDataStatus",
                TSASReviseWorkBean.class, HttpServletRequest.class));
    }

    @Test
    void destructiveEndpointsDoNotAcceptGet() {
        assertNoGetDeleteMappings(CommonController.class);
        assertNoGetDeleteMappings(SystemAdminController.class);
        assertNoGetDeleteMappings(SuperAdminController.class);
    }

    @Test
    void everyCommonControllerMutationHasExplicitMethodAuthorization() {
        for (Method method : CommonController.class.getDeclaredMethods()) {
            RequestMapping mapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
            if (mapping == null || Arrays.stream(mapping.method()).noneMatch(this::isMutation)) {
                continue;
            }
            assertThat(method.getAnnotation(PreAuthorize.class))
                    .as("CommonController.%s mutation authorization", method.getName())
                    .isNotNull();
        }
    }

    @Test
    void selectedBolaChannelsUseObjectLevelGuards() throws Exception {
        assertGuardContains("uploadInspectionImages", "@workAuthorization.canAccessWork");
        assertGuardContains("editOngoingWork", "@workAuthorization.canEditWork");
        assertGuardContains("uploadOtherDoc", "@workAuthorization.canAccessWork");
        assertGuardContains("deleteFile", "@workResourceAuthorization.canDeleteWorkDocument");
        assertGuardContains("saveOrUpdateDmRemarks", "@workAuthorization.canEditDmRemark");
        assertGuardContains("saveOrUpdateDepartmentRemarks", "@workAuthorization.canEditDepartmentRemark");
        assertGuardContains("deleteRemarks", "@workAuthorization.canDeleteDmRemark");
        assertGuardContains("deleteDepartmentRemarks", "@workAuthorization.canDeleteDepartmentRemark");
        assertGuardContains("deleteFinancialAgencyRow", "@workResourceAuthorization.canAccessFinancialAgency");
    }

    private void assertDmWorkGuard(Method method) {
        PreAuthorize annotation = method.getAnnotation(PreAuthorize.class);
        assertThat(annotation).isNotNull();
        assertThat(annotation.value())
                .contains("hasRole('ROLE_DM')")
                .contains("@workAuthorization.canAccessWork");
    }

    private void assertNoGetDeleteMappings(Class<?> controllerType) {
        for (Method method : controllerType.getDeclaredMethods()) {
            RequestMapping mapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
            if (mapping == null || !method.getName().toLowerCase().startsWith("delete")) {
                continue;
            }
            assertThat(mapping.method())
                    .as(controllerType.getSimpleName() + "." + method.getName())
                    .doesNotContain(RequestMethod.GET);
        }
    }

    private boolean isMutation(RequestMethod method) {
        return method == RequestMethod.POST || method == RequestMethod.PUT
                || method == RequestMethod.PATCH || method == RequestMethod.DELETE;
    }

    private void assertGuardContains(String methodName, String guard) {
        Method method = Arrays.stream(CommonController.class.getDeclaredMethods())
                .filter(candidate -> candidate.getName().equals(methodName))
                .findFirst()
                .orElseThrow();
        assertThat(method.getAnnotation(PreAuthorize.class).value()).contains(guard);
    }
}
