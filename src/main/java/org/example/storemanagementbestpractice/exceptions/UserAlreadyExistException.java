package org.example.storemanagementbestpractice.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserAlreadyExistException extends AuthenticationException {
    public final static String USER_ALREADY_EXIST = "user already exist";
    public UserAlreadyExistException(String message){
        super(message);
    }
}
