package com.movie.me.service;

import java.util.List;

import com.movie.me.domain.*;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void createUser(String email, String username, String password)
            throws EmailAlreadyExistsException, UsernameAlreadyExistsException;

	User getUser(String username);
    List<Movie> getLikes(String username);
    Movie addLike(String username, String imdbid)
            throws UserDoesNotExistException, MovieDoesNotExistException;

    void removeLike(String username, String imdbid)
            throws UserDoesNotExistException, MovieDoesNotExistException;
    List<Movie> getRecommendations(String username);
}