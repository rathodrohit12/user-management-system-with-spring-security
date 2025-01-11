package com.rohit.usermanagementsystem.exception;

/**
 * Custom exception to indicate that a user with the specified mobile number already exists.
 */
public class MobileNumberAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new MobileNumberAlreadyExistsException with the specified detail message.
     *
     * @param message The detail message for the exception.
     */
    public MobileNumberAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Constructs a new MobileNumberAlreadyExistsException with the specified detail message
     * and cause.
     *
     * @param message The detail message for the exception.
     * @param cause   The cause of the exception.
     */
    public MobileNumberAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}