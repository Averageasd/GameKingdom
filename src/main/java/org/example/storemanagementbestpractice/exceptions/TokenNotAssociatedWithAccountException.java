package org.example.storemanagementbestpractice.exceptions;

import org.springframework.security.core.AuthenticationException;

public class TokenNotAssociatedWithAccountException extends AuthenticationException {
    public static final String TOKEN_NOT_ASSOCIATED_WITH_ACCOUNT="Token not associated with account";
    public TokenNotAssociatedWithAccountException(String message) {
        super(message);
    }
}
