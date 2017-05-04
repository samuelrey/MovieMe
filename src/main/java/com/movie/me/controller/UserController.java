package com.movie.me.controller;

import java.util.List;

import com.movie.me.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.movie.me.service.UserService;

/**
 * Defines Http endpoints for User related resources.
 *
 * @author  Samuel Villavicencio
 * @since   03-05-17
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

    /**
     * Creates a new User with the given fields.
     * @param email
     * @param username
     * @param password
     * @return  OK.
     * @throws UsernameAlreadyExistsException
     * @throws EmailAlreadyExistsException
     */
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestParam(value="email") String email,
                                        @RequestParam(value="username") String username,
                                        @RequestParam(value="password") String password)
            throws UsernameAlreadyExistsException, EmailAlreadyExistsException {
        userService.createUser(email, username, password);

        return new ResponseEntity<Void>(null, null, HttpStatus.OK);
    }

    /**
     * Authenticates User with provided credentials.
     * @param username  User to be authenticated.
     * @param password  Password of the given User.
     * @return  Access token for future requests in X-token header.
     */
	@RequestMapping(value="/login", method=RequestMethod.POST)
    public ResponseEntity<?> login(@RequestHeader(value="username") String username,
                                   @RequestHeader(value="password") String password) {
	    final String token = userService.authenticate(username, password);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-token", token);
        return new ResponseEntity<Void>(null, headers, HttpStatus.OK);
    }

    /**
     * Finds a unique User based on username.
     * @param username  User to be returned.
     * @return  User as JSON object.
     * @throws UserDoesNotExistException
     */
    @RequestMapping(value="/{username}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable(value="username") String username) throws UserDoesNotExistException {
        return userService.getUser(username);
    }

    /**
     * Creates like relationship from User to Movie.
     * @param username  User to have like added.
     * @param imdbid    Movie the User likes.
     * @return OK
     * @throws UserDoesNotExistException
     * @throws MovieDoesNotExistException
     */
    @RequestMapping(value="/{username}/likes", method=RequestMethod.POST)
    public ResponseEntity<?> addLike(@PathVariable(value="username") String username,
                                     @RequestParam(value="imdbid") String imdbid)
            throws UserDoesNotExistException, MovieDoesNotExistException {
        userService.addLike(username, imdbid);
        return new ResponseEntity<Void>(null, null, HttpStatus.OK);
    }

    /**
     * Finds Movies liked by User.
     * @param username  User to have likes returned.
     * @param page  Nth page of the entire set of likes.
     * @param size  Number of Movies per page.
     * @return  Movies liked by User.
     * @throws UserDoesNotExistException
     */
	@RequestMapping(value="/{username}/likes", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> getLikes(@PathVariable(value="username") String username, @Param(value="page") Integer page,
                                @Param(value="size") Integer size) throws UserDoesNotExistException {
	    return userService.getLikes(username, page, size);
	}

    /**
     * Removes like relationship between User and Movie.
     * @param username  User to have like removed.
     * @param imdbid    Movie the User does not like.
     * @throws UserDoesNotExistException
     * @throws MovieDoesNotExistException
     */
    @RequestMapping(value="/{username}/likes/{imdbid}", method=RequestMethod.DELETE)
	public ResponseEntity<?> removeLike(@PathVariable(value="username") String username,
                                        @PathVariable(value="imdbid") String imdbid)
            throws UserDoesNotExistException, MovieDoesNotExistException {
		userService.removeLike(username, imdbid);
		return new ResponseEntity<Void>(null, null, HttpStatus.OK);
	}

    /**
     * Finds Movie recommendations based on those liked by other Users with similar likes.
     * @param username  User to get recommendations for.
     * @param page  Nth page of the entire set of likes.
     * @param size  Number of Movies per page.
     * @return  User based recommendations.
     * @throws UserDoesNotExistException
     */
    @RequestMapping(value="/{username}/recommendations", method=RequestMethod.GET,
            produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> getUserBasedRecommendations(@PathVariable(value="username") String username,
                                                   @Param(value="page") Integer page, @Param(value="size") Integer size)
            throws UserDoesNotExistException {
	    return userService.getUserBasedRecommendations(username, page, size);
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
