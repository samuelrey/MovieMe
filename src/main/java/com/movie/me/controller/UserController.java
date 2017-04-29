package com.movie.me.controller;

import java.util.List;

import com.movie.me.JwtTokenUtil;
import com.movie.me.domain.*;
import com.movie.me.service.InputUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.movie.me.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    InputUtil inputUtil;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserDetailsService userDetailsService;

	@Autowired
	UserService userService;

	@RequestMapping(value="/login", method=RequestMethod.POST)
    public ResponseEntity<?> login(@RequestHeader(value="user") String user, @RequestHeader(value="pass") String pass) {

        final Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user, pass)
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(user);
        final String token = jwtTokenUtil.generateToken(userDetails);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Token", token);
        return new ResponseEntity<Void>(null, headers, HttpStatus.OK);
    }

	@RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestParam(value="email") String email,
                                        @RequestParam(value="username") String username,
                                        @RequestParam(value="password") String password,
                                        @RequestParam(value="check") String check)
            throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
	    if( !inputUtil.validEmail(email) ) {
            throw new IllegalArgumentException(String.format("Invalid email: %s", email));
        }
        if( !inputUtil.validUsername(username) ) {
            throw new IllegalArgumentException(String.format("Invalid username: %s", username));
        }
        if( !inputUtil.validPassword(password, check) ) {
	        throw new IllegalArgumentException("Invalid password.");
        }
        userService.createUser(email, username, password);
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Login", "https://localhost:8080/user/login");
	    return new ResponseEntity<Void>(null, headers, HttpStatus.OK);
    }

    // TODO: return User without relationships
    @RequestMapping(value="/{username}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable(value="username") String username) throws UserDoesNotExistException {
	    if(!inputUtil.validUsername(username)) {
            throw new IllegalArgumentException(String.format("Invalid username: %s", username));
        }
        return userService.getUser(username);
    }

    //
    @RequestMapping(value="/{username}/likes", method=RequestMethod.POST)
    public void addLike(@PathVariable(value="username") String username, @RequestParam(value="imdbid") String imdbid)
            throws UserDoesNotExistException, MovieDoesNotExistException {
	    if(!inputUtil.validUsername(username)) {
	        throw new IllegalArgumentException(String.format("Invalid username: %s", username));
        }
        if(!inputUtil.validImdbid(imdbid)) {
            throw new IllegalArgumentException(String.format("Invalid imdbid: %s", imdbid));
        }
        userService.addLike(username, imdbid);
    }

	@RequestMapping(value="/{username}/likes", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> getLikes(@PathVariable(value="username") String username) throws UserDoesNotExistException {
        if(!inputUtil.validUsername(username)) {
            throw new IllegalArgumentException(String.format("Invalid username: %s", username));
        }
	    return userService.getLikes(username);
	}

    @RequestMapping(value="/{username}/likes/{imdbid}", method=RequestMethod.DELETE)
	public void removeLike(@PathVariable(value="username") String username, @PathVariable(value="imdbid") String imdbid)
            throws UserDoesNotExistException, MovieDoesNotExistException {
        if(!inputUtil.validUsername(username)) {
            throw new IllegalArgumentException(String.format("Invalid username: %s", username));
        }
        if(!inputUtil.validImdbid(imdbid)) {
            throw new IllegalArgumentException(String.format("Invalid imdbid: %s", imdbid));
        }
		userService.removeLike(username, imdbid);
	}

    @RequestMapping(value="/{username}/recommendations", method=RequestMethod.GET,
            produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> getRecommendations(@PathVariable(value="username") String username)
            throws UserDoesNotExistException {
        if(!inputUtil.validUsername(username)) {
            throw new IllegalArgumentException(String.format("Invalid username: %s", username));
        }
	    return userService.getRecommendations(username);
    }

    @ExceptionHandler({ UserDoesNotExistException.class, MovieDoesNotExistException.class })
    ResponseEntity<String> handleNotFound(Exception e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ UsernameAlreadyExistsException.class, EmailAlreadyExistsException.class })
    ResponseEntity<String> handleDuplicate(Exception e) {
	    return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    ResponseEntity<String> handleInvalidInput(Exception e) {
	    return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
