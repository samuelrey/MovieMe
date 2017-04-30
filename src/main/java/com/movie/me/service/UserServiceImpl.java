package com.movie.me.service;

import com.movie.me.domain.*;
import com.movie.me.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import com.movie.me.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void createUser(String email, String username, String password)
            throws EmailAlreadyExistsException, UsernameAlreadyExistsException {
        if( userRepository.findByEmail(email) != null ) {
            throw new EmailAlreadyExistsException(email);
        }
        if( userRepository.findByUsername(username) != null ) {
            throw new UsernameAlreadyExistsException(username);
        }
        User user = new User.UserBuilder()
                .withEmail(email)
                .withUsername(username)
                .withPassword(passwordEncoder.encode(password))
                .build();

        userRepository.save(user);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public List<Movie> getLikes(String username) {
        return movieRepository.findMoviesLikedBy(username);
    }
    
    public Movie addLike(String username, String imdbid) throws UserDoesNotExistException, MovieDoesNotExistException {
        User user = userRepository.findByUsername(username);
        Movie movie = movieRepository.findByImdbid(imdbid);
        if( user == null ) {
            throw new UserDoesNotExistException(username);
        }
        if ( movie == null ) {
            throw new MovieDoesNotExistException(imdbid);
        }
        user.addLike(movie);
        userRepository.save(user);
        return movie;
    }

    public void removeLike(String username, String imdbid)
            throws UserDoesNotExistException, MovieDoesNotExistException {
        User user = userRepository.findByUsername(username);
        Movie movie = movieRepository.findByImdbid(imdbid);
        if( user == null ) {
            throw new UserDoesNotExistException(username);
        }
        if ( movie == null ) {
            throw new MovieDoesNotExistException(imdbid);
        }
        movieRepository.removeUserLikesMovie(username, imdbid);
    }

    public List<Movie> getRecommendations(String username) {
        if( username == null ) {
            return new ArrayList<>();
        }

        return movieRepository.findRecommendationsFor(username, 0, 0);
    }
}
