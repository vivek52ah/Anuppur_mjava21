package com.anuppur.security;

import java.io.IOException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** Reject TRACE before application or authentication processing can reflect request data. */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TraceMethodFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        if ("TRACE".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            response.setHeader("Allow", "GET, HEAD, POST, PUT, DELETE, OPTIONS");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"status\":405,\"error\":\"Method Not Allowed\","
                    + "\"message\":\"TRACE method is disabled.\"}");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
