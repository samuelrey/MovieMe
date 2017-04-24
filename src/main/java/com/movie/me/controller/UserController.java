package com.movie.me.controller;

import java.util.List;

import com.movie.me.domain.*;
import com.movie.me.service.InputValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.movie.me.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
    InputValidatorService inputValidatorService;

	@RequestMapping(method=RequestMethod.POST)
    public void createUser(@RequestParam(value="email") String email, @RequestParam(value="username") String username,
                           @RequestParam(value="photo", required=false) String photo)
            throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
	    if(!inputValidatorService.validEmail(email)) {
            throw new IllegalArgumentException(String.format("Invalid email: %s", email));
        }
        if(!inputValidatorService.validUsername(username)) {
            throw new IllegalArgumentException(String.format("Invalid username: %s", username));
        }
        userService.createUser(email, username, photo);
    }

    @RequestMapping(value="/{username}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable(value="username") String username) throws UserDoesNotExistException {
	    if(!inputValidatorService.validUsername(username)) {
            throw new IllegalArgumentException(String.format("Invalid username: %s", username));
        }
        return userService.getUser(username);
    }

    @RequestMapping(value="/{username}/likes", method=RequestMethod.POST)
    public void addLike(@PathVariable(value="username") String username, @RequestParam(value="imdbid") String imdbid)
            throws UserDoesNotExistException, MovieDoesNotExistException {
	    if(!inputValidatorService.validUsername(username)) {
	        throw new IllegalArgumentException(String.format("Invalid username: %s", username));
        }
        if(!inputValidatorService.validImdbid(imdbid)) {
            throw new IllegalArgumentException(String.format("Invalid imdbid: %s", imdbid));
        }
        userService.addLike(username, imdbid);
    }

	@RequestMapping(value="/{username}/likes", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> getLikes(@PathVariable(value="username") String username) throws UserDoesNotExistException {
        if(!inputValidatorService.validUsername(username)) {
            throw new IllegalArgumentException(String.format("Invalid username: %s", username));
        }
	    return userService.getLikes(username);
	}

    @RequestMapping(value="/{username}/likes/{imdbid}", method=RequestMethod.DELETE)
	public void removeLike(@PathVariable(value="username") String username, @PathVariable(value="imdbid") String imdbid)
            throws UserDoesNotExistException, MovieDoesNotExistException {
        if(!inputValidatorService.validUsername(username)) {
            throw new IllegalArgumentException(String.format("Invalid username: %s", username));
        }
        if(!inputValidatorService.validImdbid(imdbid)) {
            throw new IllegalArgumentException(String.format("Invalid imdbid: %s", imdbid));
        }
		userService.removeLike(username, imdbid);
	}

    @RequestMapping(value="/{username}/recommendations", method=RequestMethod.GET,
            produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> getRecommendations(@PathVariable(value="username") String username)
            throws UserDoesNotExistException {
        if(!inputValidatorService.validUsername(username)) {
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
