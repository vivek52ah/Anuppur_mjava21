package com.anuppur.security;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class PlainTextRequestInterceptor implements HandlerInterceptor {

    private final UserSuppliedTextPolicy textPolicy;

    public PlainTextRequestInterceptor(UserSuppliedTextPolicy textPolicy) {
        this.textPolicy = textPolicy;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        for (Map.Entry<String, String[]> parameter : request.getParameterMap().entrySet()) {
            textPolicy.validateParameter(parameter.getKey(), parameter.getValue());
        }
        return true;
    }
}
