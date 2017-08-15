package com.movie.me.domain.exceptions;

public class UsernameAlreadyExistsException extends Exception {
    private static final String MESSAGE = "Username '%s' already exists.";

    public UsernameAlreadyExistsException(String username) {
        super(String.format(MESSAGE, username));
    }
}
