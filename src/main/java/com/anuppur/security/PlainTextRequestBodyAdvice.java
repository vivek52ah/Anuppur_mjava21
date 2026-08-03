package com.anuppur.security;

import java.lang.reflect.Type;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

@ControllerAdvice
public class PlainTextRequestBodyAdvice extends RequestBodyAdviceAdapter {

    private final UserSuppliedTextPolicy textPolicy;

    public PlainTextRequestBodyAdvice(UserSuppliedTextPolicy textPolicy) {
        this.textPolicy = textPolicy;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
            Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        textPolicy.validateBody(body);
        return body;
    }
}
