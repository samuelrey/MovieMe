package com.movie.me.domain;

public class MovieDoesNotExistException extends Exception {
    private static final String MESSAGE = "Movie '%s' does not exist.";

    public MovieDoesNotExistException(String imdbid) {
        super(String.format(MESSAGE, imdbid));
    }
}
