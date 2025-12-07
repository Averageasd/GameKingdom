package org.example.storemanagementbestpractice.exceptions;

import java.util.NoSuchElementException;

public class GameNotExistException extends NoSuchElementException {
    public static final String GAME_NOT_EXIST = "Game does not exist";
    public GameNotExistException(String message) {
        super(message);
    }
}
