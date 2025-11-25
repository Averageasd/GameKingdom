package org.example.storemanagementbestpractice.exceptions;

import org.springframework.security.core.AuthenticationException;

public class AccountAlreadyVerifiedException extends AuthenticationException {
    public static String ACCOUNT_ALREADY_VERIFIED = "Account already verified";
    public AccountAlreadyVerifiedException(String message) {
        super(message);
    }
}
