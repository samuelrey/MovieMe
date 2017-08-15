package com.movie.me.service;

import com.movie.me.security.JwtTokenUtil;
import com.movie.me.domain.exceptions.EmailAlreadyExistsException;
import com.movie.me.domain.exceptions.MovieDoesNotExistException;
import com.movie.me.domain.exceptions.UserDoesNotExistException;
import com.movie.me.domain.exceptions.UsernameAlreadyExistsException;
import com.movie.me.domain.models.Movie;
import com.movie.me.domain.models.User;
import com.movie.me.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import com.movie.me.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceImpl implements UserService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserDetailsService userDetailsService;

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

    public String authenticate(String username, String password) throws AuthenticationException {
        final Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public List<Movie> getLikes(String username, int page, int size) throws UserDoesNotExistException {
        if( page < 1 || size < 1 ) {
            throw new IllegalArgumentException();
        }

        if( getUser(username) == null ) {
            throw new UserDoesNotExistException(username);
        }

        return movieRepository.findMoviesLikedBy(username, page, size);
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

    public Movie removeLike(String username, String imdbid)
            throws UserDoesNotExistException, MovieDoesNotExistException {
        User user = userRepository.findByUsername(username);
        Movie movie = movieRepository.findByImdbid(imdbid);
        if( user == null ) {
            throw new UserDoesNotExistException(username);
        }
        if ( movie == null ) {
            throw new MovieDoesNotExistException(imdbid);
        }
        return movieRepository.removeUserLikesMovie(username, imdbid);
    }

    public List<Movie> getUserBasedRecommendations(String username, int page, int size) {
        return movieRepository.findUserBasedRecommendations(username, page, size);
    }
}
