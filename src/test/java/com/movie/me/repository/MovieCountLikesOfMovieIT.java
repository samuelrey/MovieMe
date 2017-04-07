package com.movie.me.repository;

import com.movie.me.beans.Neo4jTestConfiguration;
import com.movie.me.domain.Movie;
import com.movie.me.domain.User;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Neo4jTestConfiguration.class)
@ActiveProfiles(profiles = "test")
public class MovieCountLikesOfMovieIT {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    private Movie newHope;
    private User samuel;

    @Before
    public void initialize() {
        newHope = new Movie();
        newHope.setTitle("Star Wars: Episode IV - A New Hope");
        newHope.setImdbid("0004");

        samuel = new User();

        movieRepository.save(newHope);
        userRepository.save(samuel);
    }

    @Test
    @DirtiesContext
    public void testCountLikesOfMovieIsZero() {
        int count = movieRepository.countLikesOf("0004");

        assertThat(count, Matchers.equalTo(0));
    }

    @Test
    @DirtiesContext
    public void testCountLikesOfMovieIsMoreThanZero() {
        samuel.setMoviesLiked(new HashSet<>(Arrays.asList(newHope)));
        userRepository.save(samuel);

        int count = movieRepository.countLikesOf("0004");

        assertThat(count, Matchers.equalTo(1));
    }
}
