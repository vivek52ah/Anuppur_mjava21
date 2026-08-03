package com.anuppur.exception;

import java.sql.SQLException;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.persistence.PersistenceException;
import jakarta.servlet.http.HttpServletRequest;

/** Prevents exception, SQL, schema and table details from leaking to clients. */
@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            NumberFormatException.class,
            IllegalArgumentException.class,
            HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class
    })
    public ResponseEntity<Map<String, Object>> badRequest(Exception exception, HttpServletRequest request) {
        return response(HttpStatus.BAD_REQUEST, "Invalid request.", request, null);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> forbidden(AccessDeniedException exception,
            HttpServletRequest request) {
        return response(HttpStatus.FORBIDDEN, "Access denied.", request, null);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, Object>> notFound(NoResourceFoundException exception,
            HttpServletRequest request) {
        return response(HttpStatus.NOT_FOUND, "Resource not found.", request, null);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> methodNotAllowed(
            HttpRequestMethodNotSupportedException exception, HttpServletRequest request) {
        return response(HttpStatus.METHOD_NOT_ALLOWED, "Request method is not supported.", request, null);
    }

    @ExceptionHandler({DataAccessException.class, PersistenceException.class, SQLException.class})
    public ResponseEntity<Map<String, Object>> databaseFailure(Exception exception, HttpServletRequest request) {
        return internalError(exception, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> unexpectedFailure(Exception exception, HttpServletRequest request) {
        return internalError(exception, request);
    }

    private ResponseEntity<Map<String, Object>> internalError(Exception exception, HttpServletRequest request) {
        String reference = UUID.randomUUID().toString();
        logger.error("Unhandled request failure, reference={}", reference, exception);
        return response(HttpStatus.INTERNAL_SERVER_ERROR,
                "The request could not be completed.", request, reference);
    }

    private ResponseEntity<Map<String, Object>> response(HttpStatus status, String message,
            HttpServletRequest request, String reference) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("path", request.getRequestURI());
        if (reference != null) {
            body.put("reference", reference);
        }
        return ResponseEntity.status(status).body(body);
    }
}
