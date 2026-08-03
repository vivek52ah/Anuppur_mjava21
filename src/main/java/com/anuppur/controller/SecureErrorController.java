package com.anuppur.controller;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

/** Generic fallback for container-level errors that do not pass through controller advice. */
@RestController
public class SecureErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus status = resolveStatus(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", status.is4xxClientError() ? "Invalid request." : "The request could not be completed.");
        body.put("path", request.getRequestURI());
        return ResponseEntity.status(status).body(body);
    }

    private HttpStatus resolveStatus(Object statusAttribute) {
        if (statusAttribute instanceof Integer statusCode) {
            HttpStatus resolved = HttpStatus.resolve(statusCode);
            if (resolved != null) {
                return resolved;
            }
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
