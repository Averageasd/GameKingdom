package org.example.storemanagementbestpractice.exceptions;

import org.springframework.security.core.AuthenticationException;

public class FailToUpdateUserPasswordException extends AuthenticationException {
    public static final String FAIL_TO_UPDATE_PASSWORD = "account is locked or does not exist";
    public FailToUpdateUserPasswordException(String message) {
        super(message);
    }
}
