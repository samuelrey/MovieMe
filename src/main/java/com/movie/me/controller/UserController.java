package com.movie.me.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.movie.me.domain.User;
import com.movie.me.domain.Movie;
import com.movie.me.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;

	@RequestMapping(method=RequestMethod.POST)
    public void createUser(@RequestParam(value="email") String email) {
        userService.createUser(email);
    }

	@RequestMapping(value="/{userid}", method=RequestMethod.GET, produces="application/json")
	public User getUser(@PathVariable(value="userid") String userid) {
		return userService.getUser(userid);
	}

	@RequestMapping(value="/{userid}/likes", method=RequestMethod.GET, produces="application/json")
    public List<Movie> getLikes(@PathVariable(value="userid") String userid) {
	    return userService.getLikes(userid);
	}

	@RequestMapping(value="/{userid}/likes", method=RequestMethod.POST)
    public void addLike(@PathVariable(value="userid") String userid, @RequestParam(value="imdbid") String imdbid) {
	    userService.addLike(userid, imdbid);
    }

    @RequestMapping(value="/{userid}/likes/{imdbid}", method=RequestMethod.DELETE)
	public void removeLike(@PathVariable(value="userid") String userid, @PathVariable(value="imdbid") String imdbid) {
		userService.removeLike(userid, imdbid);
	}

    @RequestMapping(value="/{userid}/recommendations", method=RequestMethod.GET, produces="application/json")
    public List<Movie> getRecommendations(@PathVariable(value="userid") String userid) {
	    return userService.getRecommendations(userid);
    }
}
