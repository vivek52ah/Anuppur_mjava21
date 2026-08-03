package com.anuppur.exception;

/**
 * Raised when a financial request violates a server-side business or database
 * numeric constraint.
 */
public class FinancialValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FinancialValidationException(String message) {
        super(message);
    }
}
