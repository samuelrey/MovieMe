package com.movie.me.controller;

import java.util.ArrayList;
import java.util.List;

import com.movie.me.utility.GoogleUserAuthentication;
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

	@RequestMapping(value="/{email}", method=RequestMethod.GET, produces="application/json")
	public User getUser(@PathVariable(value="email") String email ) {
		return userService.getUser(email);
	}

    @RequestMapping(value="/user/signin", method=RequestMethod.GET, produces="application/json")
    public User userSignIn(@RequestParam(value="idToken") String idToken) {
        User user = GoogleUserAuthentication.authenticate(idToken);
        if(user != null) {
            return userService.userSignIn(user);
        }

        return null;
    }

    @RequestMapping(value="/user/search", method=RequestMethod.GET, produces="application/json")
    public List<User> searchForUser(@RequestParam(value="name") String name) {
        return userService.findByNameLike(name);
    }
    
    @RequestMapping(value="/user/view_likes", method=RequestMethod.GET, produces="application/json")
    public List<Movie> moviesLikedByUser(@RequestParam(value="userid") String userid) {
        List<Movie> movies = userService.retrieveMoviesLikedBy(userid);
        return movies;
    }

    /*@RequestMapping(value="user/adds_friend", method=RequestMethod.GET, produces="application/json")
    public User addUserFriendsUser(@RequestParam(value="userid1") String userid1, @RequestParam(value="userid2") String userid2) {
        return userService.addUserFriendsUser(userid1, userid2);
    }

    @RequestMapping(value="user/removes_friend", method=RequestMethod.GET, produces="application/json")
    public User userRemovesFriends(@RequestParam(value="userid1") String userid1, @RequestParam(value="userid2") String userid2) {
        return userService.userRemovesFriend(userid1, userid2);
    }

    @RequestMapping(value="user/retrieve_friends", method=RequestMethod.GET, produces="application/json")
    public List<User> retrieveFriendsOf(@RequestParam(value="userid") String userid) {
        return userService.retrieveFriendsOf(userid);
    }*/
    
    @RequestMapping(value="/user/likes_movie", method=RequestMethod.GET, produces="application/json")
    public Movie userLikesMovie(@RequestParam(value="userid") String userid, @RequestParam(value="imdbid") String imdbid) {
        return userService.addUserLikesMovie(userid, imdbid);
    }

    @RequestMapping(value="/user/unlikes_movie", method=RequestMethod.GET, produces="application/json")
    public Movie userUnlikesMovie(@RequestParam(value="userid") String userid, @RequestParam(value="imdbid") String imdbid) {
        return userService.userUnlikesMovie(userid, imdbid);
    }

    @RequestMapping(value="/movie/recommendations", method=RequestMethod.GET, produces="application/json")
    public List<Movie> getRecommendationForUser(@RequestParam(value="userid") String userid) {
        return userService.getRecommendationForUser(userid);
    }
}
