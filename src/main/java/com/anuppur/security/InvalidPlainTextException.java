package com.anuppur.security;

public class InvalidPlainTextException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidPlainTextException(String message) {
        super(message);
    }
}
