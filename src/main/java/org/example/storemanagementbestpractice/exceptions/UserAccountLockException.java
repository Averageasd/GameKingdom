package org.example.storemanagementbestpractice.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserAccountLockException extends AuthenticationException {
    public static final String USER_IS_LOCKED = "user account is locked";
    public UserAccountLockException(String message) {
        super(message);
    }
}
