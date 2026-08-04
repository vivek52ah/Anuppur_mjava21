package com.anuppur.security;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Optional;
import java.util.Set;

import com.anuppur.bean.UserBean;
import com.anuppur.controller.SystemAdminController;
import com.anuppur.constants.DMSConstants;
import com.anuppur.entity.District;
import com.anuppur.entity.Role;
import com.anuppur.entity.Users;
import com.anuppur.repository.UserRepository;
import com.anuppur.repository.WorkRepository;
import com.anuppur.repository.WorkStatusRepository;
import com.anuppur.service.CommonService;
import com.anuppur.service.NotificationService;
import com.anuppur.service.SuperAdminService;
import com.anuppur.service.SystemAdminService;
import com.anuppur.service.UserService;

@SpringJUnitWebConfig(SystemAdminDepartmentAuthorizationWebTest.TestConfiguration.class)
class SystemAdminDepartmentAuthorizationWebTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    void departmentCanAddAreaOfficer() throws Exception {
        mockMvc.perform(post("/systemAdmin/addUser")
                        .with(user("department").roles("DEPARTMENT"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"designationId\":1,\"role\":{\"roleCode\":\"ROLE_AREA_OFFICER\"}}"))
                .andExpect(status().isOk());
    }

    @Test
    void departmentCannotUseAddUserToCreatePrivilegedRole() throws Exception {
        mockMvc.perform(post("/systemAdmin/addUser")
                        .with(user("department").roles("DEPARTMENT"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"designationId\":2,\"role\":{\"roleCode\":\"ROLE_DEPARTMENT\"}}"))
                .andExpect(status().isForbidden());
    }

    @Test
    void departmentCanEditAndDeleteOwnedAreaOfficer() throws Exception {
        String areaOfficer = "{\"id\":10,\"designationId\":1,"
                + "\"role\":{\"roleCode\":\"ROLE_AREA_OFFICER\"},"
                + "\"emailId\":\"officer@example.com\"}";

        mockMvc.perform(post("/systemAdmin/editUser")
                        .with(user("department").roles("DEPARTMENT"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(areaOfficer))
                .andExpect(status().isOk());

        mockMvc.perform(post("/systemAdmin/deleteUser/10")
                        .with(user("department").roles("DEPARTMENT"))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void departmentCannotDeletePrivilegedUser() throws Exception {
        mockMvc.perform(post("/systemAdmin/deleteUser/11")
                        .with(user("department").roles("DEPARTMENT"))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Configuration
    @EnableWebMvc
    @EnableWebSecurity
    @EnableMethodSecurity
    static class TestConfiguration {

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            return http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                    .formLogin(form -> form.disable())
                    .httpBasic(basic -> { })
                    .build();
        }

        @Bean
        SystemAdminController systemAdminController() {
            return new SystemAdminController();
        }

        @Bean
        UserAuthorization userAuthorization(UserRepository userRepository) {
            return new UserAuthorization(userRepository);
        }

        @Bean
        UserRepository userRepository() {
            UserRepository repository = mock(UserRepository.class);
            Users department = scopedUser(2L, "ROLE_DEPARTMENT");
            Users areaOfficer = scopedUser(1L, "ROLE_AREA_OFFICER");
            Users privileged = scopedUser(2L, "ROLE_DEPARTMENT");
            when(repository.findByUsernameAndStatus("department", DMSConstants.STATUS_ACTIVE))
                    .thenReturn(department);
            when(repository.findById(10L)).thenReturn(Optional.of(areaOfficer));
            when(repository.findById(11L)).thenReturn(Optional.of(privileged));
            return repository;
        }

        private static Users scopedUser(Long designationId, String roleCode) {
            Users user = new Users();
            user.setDesignationID(designationId);
            user.setDepartmentName("Roads");
            user.setDistrict(new District(7L));
            user.setRoles(Set.of(new Role(roleCode)));
            return user;
        }

        @Bean
        CommonService commonService() { return mock(CommonService.class); }

        @Bean
        UserService userService() { return mock(UserService.class); }

        @Bean
        NotificationService notificationService() { return mock(NotificationService.class); }

        @Bean
        SystemAdminService systemAdminService() { return mock(SystemAdminService.class); }

        @Bean
        WorkRepository workRepository() { return mock(WorkRepository.class); }

        @Bean
        WorkStatusRepository workStatusRepository() { return mock(WorkStatusRepository.class); }

        @Bean
        SuperAdminService superAdminService() {
            SuperAdminService service = mock(SuperAdminService.class);
            when(service.addUser(any(UserBean.class))).thenReturn(null);
            when(service.editUser(any(UserBean.class), anyString())).thenReturn(null);
            when(service.deleteUser(any(Long.class))).thenReturn(null);
            return service;
        }
    }
}
