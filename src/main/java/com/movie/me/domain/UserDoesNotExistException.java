package com.movie.me.domain;

public class UserDoesNotExistException extends Exception {
    private static final String MESSAGE = "User '%s' does not exist.";

    public UserDoesNotExistException(String username) {
        super(String.format(MESSAGE, username));
    }
}
