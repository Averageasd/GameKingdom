package org.example.storemanagementbestpractice.exceptions;

import java.util.NoSuchElementException;

public class UserNotFoundException extends NoSuchElementException {
    public static final String USER_NOT_FOUND = "user not found";
    public UserNotFoundException(String message) {
        super(message);
    }
}
