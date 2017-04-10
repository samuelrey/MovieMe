package com.movie.me.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.movie.me.domain.User;
import com.movie.me.domain.Movie;

@Service
public interface UserService {
    void createUser(String email);
	User getUser(String userid);
    List<Movie> getLikes(String userid);
    Movie addLike(String userid, String imdbid);
    Movie removeLike(String userid, String imdbid);
    List<Movie> getRecommendations(String userid);
}