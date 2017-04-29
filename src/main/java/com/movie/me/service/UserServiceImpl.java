package com.movie.me.service;

import com.movie.me.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import com.movie.me.domain.Movie;
import com.movie.me.domain.User;
import com.movie.me.repository.UserRepository;
import org.springframework.core.convert.ConverterNotFoundException;

public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

    @Autowired
    MovieRepository movieRepository;

    public void createUser(String email, String username, String photo) {
        User user = new User();
        user.setEmail(email);

        userRepository.save(user);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public List<Movie> getLikes(String userid) {
        List<Movie> movies = new ArrayList<>();
        try {
            movies = movieRepository.findMoviesLikedBy(userid);
        } catch(ConverterNotFoundException e) {
            e.printStackTrace();
        }

        return movies;
    }
    
    public Movie addLike(String userid, String imdbid) {
        return movieRepository.addUserLikesMovie(userid, imdbid);
    }

    public Movie removeLike(String userId, String imdbid) {
        return movieRepository.removeUserLikesMovie(userId, imdbid);
    }

    public List<Movie> getRecommendations(String userid) {
        if( userid == null ) {
            return new ArrayList<>();
        }

        return movieRepository.findRecommendationsFor(userid, 0, 0);
    }
}
