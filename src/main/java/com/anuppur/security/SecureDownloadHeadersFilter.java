package com.anuppur.security;

import java.io.IOException;
import java.util.Locale;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 20)
public class SecureDownloadHeadersFilter extends OncePerRequestFilter {

    private static final String CONTENT_DISPOSITION = "Content-Disposition";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        response.setHeader("X-Content-Type-Options", "nosniff");
        String uri = request.getRequestURI().toLowerCase(Locale.ROOT);
        if (!uri.contains("/download") && !uri.contains("/previewdocumentremarks/")) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletResponseWrapper attachmentOnlyResponse = new HttpServletResponseWrapper(response) {
            @Override
            public void setHeader(String name, String value) {
                super.setHeader(name, secureDisposition(name, value));
            }

            @Override
            public void addHeader(String name, String value) {
                super.addHeader(name, secureDisposition(name, value));
            }

            private String secureDisposition(String name, String value) {
                if (!CONTENT_DISPOSITION.equalsIgnoreCase(name)) {
                    return value;
                }
                if (value == null || value.isBlank()) {
                    return "attachment";
                }
                return value.replaceFirst("(?i)^\\s*inline", "attachment");
            }
        };
        attachmentOnlyResponse.setHeader(CONTENT_DISPOSITION, "attachment");
        attachmentOnlyResponse.setHeader("X-Content-Type-Options", "nosniff");
        chain.doFilter(request, attachmentOnlyResponse);
    }
}
