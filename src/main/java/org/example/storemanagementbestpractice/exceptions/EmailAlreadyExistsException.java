package org.example.storemanagementbestpractice.exceptions;

import org.springframework.security.core.AuthenticationException;

public class EmailAlreadyExistsException extends AuthenticationException {
    public static final String EMAIL_ALREADY_EXISTS = "email is already associated with an existing account";
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
