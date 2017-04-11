package com.movie.me.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.movie.me.domain.User;
import com.movie.me.domain.Movie;

@Service
public interface UserService {
    void createUser(String email, String username, String photo);
	User getUser(String username);
    List<Movie> getLikes(String username);
    Movie addLike(String username, String imdbid);
    Movie removeLike(String username, String imdbid);
    List<Movie> getRecommendations(String username);
}