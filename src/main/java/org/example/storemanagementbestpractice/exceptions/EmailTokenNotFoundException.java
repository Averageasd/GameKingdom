package org.example.storemanagementbestpractice.exceptions;

import java.util.NoSuchElementException;

public class EmailTokenNotFoundException extends NoSuchElementException {
    public static final String EMAIL_TOKEN_NOT_FOUND = "email token not found";
    public EmailTokenNotFoundException(String message) {
        super(message);
    }
}
