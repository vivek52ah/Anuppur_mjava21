package com.anuppur.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Locale;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.anuppur.constants.DMSConstants;
import com.anuppur.security.LoginProtectionService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class CaptchaAuthenticationFilter extends OncePerRequestFilter {

    private final LoginProtectionService loginProtectionService;

    public CaptchaAuthenticationFilter(LoginProtectionService loginProtectionService) {
        this.loginProtectionService = loginProtectionService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !"POST".equalsIgnoreCase(request.getMethod())
                || !"/login".equals(request.getServletPath());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            loginProtectionService.checkLoginAllowed(request.getRemoteAddr(), request.getParameter("username"));

            HttpSession session = request.getSession(false);
            String expected = session == null ? null : (String) session.getAttribute(DMSConstants.CAPTCHA_LOGIN);
            if (session != null) {
                session.removeAttribute(DMSConstants.CAPTCHA_LOGIN);
            }
            String submitted = request.getParameter("captchaText");
            if (!matches(expected, submitted)) {
                fail(request, response, new InsufficientAuthenticationException("Invalid or expired CAPTCHA."));
                return;
            }
            filterChain.doFilter(request, response);
        } catch (AuthenticationException exception) {
            fail(request, response, exception);
        }
    }

    private boolean matches(String expected, String submitted) {
        if (expected == null || submitted == null) {
            return false;
        }
        byte[] expectedBytes = expected.toUpperCase(Locale.ROOT).getBytes(StandardCharsets.UTF_8);
        byte[] submittedBytes = submitted.trim().toUpperCase(Locale.ROOT).getBytes(StandardCharsets.UTF_8);
        return MessageDigest.isEqual(expectedBytes, submittedBytes);
    }

    private void fail(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException {
        request.getSession(true).setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
        response.sendRedirect(request.getContextPath() + "/login?error");
    }
}
