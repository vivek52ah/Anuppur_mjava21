package com.anuppur.security;

import static org.mockito.ArgumentMatchers.any;
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

import com.anuppur.bean.UserBean;
import com.anuppur.controller.SystemAdminController;
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
        UserAuthorization userAuthorization() {
            return new UserAuthorization();
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
            return service;
        }
    }
}
