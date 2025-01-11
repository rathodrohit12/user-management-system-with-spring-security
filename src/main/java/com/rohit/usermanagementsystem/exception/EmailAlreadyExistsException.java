package com.rohit.usermanagementsystem.exception;

/**
 * Custom exception to indicate that a user with the specified email already exists.
 */
public class EmailAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new EmailAlreadyExistsException with the specified detail message.
     *
     * @param message The detail message for the exception.
     */
    public EmailAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Constructs a new EmailAlreadyExistsException with the specified detail message
     * and cause.
     *
     * @param message The detail message for the exception.
     * @param cause   The cause of the exception.
     */
    public EmailAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
