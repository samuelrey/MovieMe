package com.movie.me.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserTest {
    private User samuel;
    private Movie newHope;

    @Before
    public void initialize() {
        samuel = new User();
        newHope = new Movie();
    }

    @Test
    public void testSetUsernameSuccess() {
        String userid = "mm1729384";
        samuel.setUsername(userid);

        assertThat(samuel.getUsername(), equalTo(userid));
    }

    @Test
    public void testSetNameSuccess() {
        String name = "Samuel";

        assertThat(name, equalTo(name));
    }

    @Test
    public void testSetEmailSuccess() {
        String email = "samuelrey010@gmail.com";
        samuel.setEmail(email);

        assertThat(samuel.getEmail(), equalTo(email));
    }

    @Test
    public void testSetMoviesLikedSuccess() {
        HashSet<Movie> moviesLiked = new HashSet<>();
        moviesLiked.add(newHope);
        samuel.setMoviesLiked(moviesLiked);

        assertThat(samuel.getMoviesLiked(), equalTo(moviesLiked));
    }
}
