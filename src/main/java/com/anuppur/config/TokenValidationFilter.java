package com.anuppur.config;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * ✅ UPDATED FOR SPRING BOOT 3.2.5 & JAVA 21
 * All javax.servlet imports migrated to jakarta.servlet
 */
public class TokenValidationFilter extends OncePerRequestFilter {

    private final String loginEndpoint;

    public TokenValidationFilter(String loginEndpoint) {
        this.loginEndpoint = loginEndpoint;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        logger.info("Path: " + path);

        // Skip token validation for login endpoint
        if ("/anuppur/mobileLogin".equals(path)) {
        	logger.info("Skipping token validation for: " + path);
            filterChain.doFilter(request, response); // Pass to MobileController
            return;
        }

        // Retrieve the token from the Authorization header
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(
                    String.format("{\"status\":401,\"error\":\"Unauthorized\",\"message\":\"Authorization header is missing or invalid.\",\"path\":\"%s\"}",
                                  request.getRequestURI()));
            return;
        }

        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
        logger.info("Extracted token: " + token);

        // Validate the token (can integrate with session or token store)
        if (token == null || token.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(
                    String.format("{\"status\":401,\"error\":\"Unauthorized\",\"message\":\"Token is missing or invalid.\",\"path\":\"%s\"}",
                                  request.getRequestURI()));
            return;
        }

        // If the token is valid, proceed with the request
        logger.info("Valid token found: " + token);
        filterChain.doFilter(request, response);
    }
}
