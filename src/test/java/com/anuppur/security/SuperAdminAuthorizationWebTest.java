package com.anuppur.security;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import com.anuppur.controller.SuperAdminController;
import com.anuppur.service.SuperAdminService;
import com.anuppur.service.UserService;

@SpringJUnitWebConfig(SuperAdminAuthorizationWebTest.TestConfiguration.class)
class SuperAdminAuthorizationWebTest {

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
    void departmentCannotAddUser() throws Exception {
        mockMvc.perform(post("/superAdmin/addUser")
                        .with(user("department").roles("DEPARTMENT"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isForbidden());
    }

    @Test
    void dmCanAddUser() throws Exception {
        mockMvc.perform(post("/superAdmin/addUser")
                        .with(user("dm").roles("DM"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUserRejectsGetAndInvalidRole() throws Exception {
        mockMvc.perform(get("/superAdmin/deleteUser/7")
                        .with(user("dm").roles("DM")))
                .andExpect(status().isMethodNotAllowed());

        mockMvc.perform(post("/superAdmin/deleteUser/7")
                        .with(user("department").roles("DEPARTMENT"))
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    void authorizedDeleteStillRequiresCsrfToken() throws Exception {
        mockMvc.perform(post("/superAdmin/deleteUser/7")
                        .with(user("dm").roles("DM")))
                .andExpect(status().isForbidden());

        mockMvc.perform(post("/superAdmin/deleteUser/7")
                        .with(user("dm").roles("DM"))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Configuration
    @EnableWebMvc
    @EnableWebSecurity
    @EnableMethodSecurity
    static class TestConfiguration {

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            return http
                    .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                    .formLogin(form -> form.disable())
                    .httpBasic(basic -> { })
                    .build();
        }

        @Bean
        SuperAdminController superAdminController() {
            return new SuperAdminController();
        }

        @Bean
        UserService userService() {
            return mock(UserService.class);
        }

        @Bean
        SuperAdminService superAdminService() {
            SuperAdminService service = mock(SuperAdminService.class);
            when(service.addUser(any(UserBean.class))).thenReturn(null);
            when(service.deleteUser(any(Long.class))).thenReturn(null);
            return service;
        }
    }
}
