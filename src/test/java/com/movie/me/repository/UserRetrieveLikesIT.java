package com.movie.me.repository;

import com.movie.me.beans.Neo4jTestConfiguration;
import com.movie.me.domain.Movie;
import com.movie.me.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Neo4jTestConfiguration.class)
@ActiveProfiles(profiles = "test")
public class UserRetrieveLikesIT {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    private User clarissa, samuel;
    private Movie newHope;

    @Before
    public void initialize() {

        samuel = new User();
        samuel.setUsername("sammy123");
        samuel.setEmail("savillavicencio@csumb.edu");

        clarissa = new User();
        clarissa.setUsername("clari123");
        clarissa.setEmail("cvasquez-ramo@csumb.edu");

        newHope = new Movie();
        newHope.setTitle("Star Wars: Episode IV - A New Hope");
        newHope.setImdbid("0004");

        userRepository.save(samuel);
        userRepository.save(clarissa);
        movieRepository.save(newHope);
        movieRepository.save(Arrays.asList(newHope));
    }

    @Test
    @DirtiesContext
    public void testRetrieveMoviesLikedByNonexistentUser() {
        List<Movie> result = movieRepository.findMoviesLikedBy("Pearce");
        assertThat(result.isEmpty(), is(true));
    }

    @Test
    @DirtiesContext
    public void testRetrieveMoviesLikedByExistentUser() {
        movieRepository.addUserLikesMovie("sammy123", "0004");
        List<Movie> result = movieRepository.findMoviesLikedBy("sammy123");
        assertThat(result.size(), equalTo(1));
        assertThat(result.get(0), equalTo(newHope));
        assertThat(result instanceof List<?>, is(true));
    }

}
