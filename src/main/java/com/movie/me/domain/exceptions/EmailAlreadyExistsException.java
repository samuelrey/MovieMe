package com.movie.me.domain.exceptions;

public class EmailAlreadyExistsException extends Exception {
    private static final String MESSAGE = "Email '%s' already exists.";

    public EmailAlreadyExistsException(String email) {
        super(String.format(MESSAGE, email));
    }
}
