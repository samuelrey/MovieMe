package com.movie.me.service;

import java.util.List;

import com.movie.me.domain.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void createUser(String email, String username, String password)
            throws EmailAlreadyExistsException, UsernameAlreadyExistsException;

    String authenticate(String username, String password)
            throws AuthenticationException;

	User getUser(String username);

    List<Movie> getLikes(String username, int page, int size) throws UserDoesNotExistException;

    Movie addLike(String username, String imdbid)
            throws UserDoesNotExistException, MovieDoesNotExistException;

    Movie removeLike(String username, String imdbid)
            throws UserDoesNotExistException, MovieDoesNotExistException;

    List<Movie> getUserBasedRecommendations(String username, int page, int size) throws UserDoesNotExistException;
}