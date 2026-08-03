package com.anuppur.security;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final LoginProtectionService loginProtectionService;

    public LoginAuthenticationFailureHandler(LoginProtectionService loginProtectionService) {
        super("/login?error");
        this.loginProtectionService = loginProtectionService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof BadCredentialsException) {
            loginProtectionService.recordAuthenticationFailure(request.getParameter("username"));
        }
        super.onAuthenticationFailure(request, response, exception);
    }
}
