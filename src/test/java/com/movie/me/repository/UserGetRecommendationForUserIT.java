package com.movie.me.repository;

import com.movie.me.beans.Neo4jTestConfiguration;
import com.movie.me.domain.Movie;
import com.movie.me.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Neo4jTestConfiguration.class)
@ActiveProfiles(profiles = "test")
public class UserGetRecommendationForUserIT {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    @DirtiesContext
    public void testGetRecommendationForUserNoSharedMoviesNoRecommendation() {
        User samuel = new User();
        User christian = new User();

        Movie goneGirl = new Movie();
        Movie guardians = new Movie();

        movieRepository.save(goneGirl);
        movieRepository.save(guardians);

        samuel.setUsername("0004");
        christian.setUsername("0008");
        samuel.setMoviesLiked(new HashSet<>(Arrays.asList(guardians)));
        christian.setMoviesLiked(new HashSet<>(Arrays.asList(goneGirl)));

        userRepository.save(samuel);
        userRepository.save(christian);

        List<Movie> recommendationsForSamuel = movieRepository.findRecommendationsFor("0004", 0, 0);
        List<Movie> recommendationsForChristian = movieRepository.findRecommendationsFor("0008", 0, 0);

        assertThat(recommendationsForSamuel.isEmpty(), is(true));
        assertThat(recommendationsForChristian.isEmpty(), is(true));
    }

    @Test
    @DirtiesContext
    public void testGetRecommendationForUserAllSharedMoviesNoRecommendation() {
        User samuel = new User();
        User christian = new User();

        Movie pirates = new Movie();

        movieRepository.save(pirates);

        samuel.setUsername("0004");
        christian.setUsername("0008");
        samuel.setMoviesLiked(new HashSet<>(Arrays.asList(pirates)));
        christian.setMoviesLiked(new HashSet<>(Arrays.asList(pirates)));

        userRepository.save(samuel);
        userRepository.save(christian);

        List<Movie> recommendationsForSamuel = movieRepository.findRecommendationsFor("0004", 0, 0);
        List<Movie> recommendationsForChristian = movieRepository.findRecommendationsFor("0008", 0, 0);

        assertThat(recommendationsForSamuel.isEmpty(), is(true));
        assertThat(recommendationsForChristian.isEmpty(), is(true));
    }

    @Test
    @DirtiesContext
    public void testGetRecommendationForUserResultsNotAlreadyLiked() {
        User samuel = new User();
        User christian = new User();

        Movie goneGirl = new Movie();
        Movie guardians = new Movie();

        movieRepository.save(goneGirl);
        movieRepository.save(guardians);

        samuel.setUsername("0004");
        christian.setUsername("0008");
        samuel.setMoviesLiked(new HashSet<>(Arrays.asList(guardians)));
        christian.setMoviesLiked(new HashSet<>(Arrays.asList(goneGirl, guardians)));

        userRepository.save(samuel);
        userRepository.save(christian);

        List<Movie> recommendationsForSamuel = movieRepository.findRecommendationsFor("0004", 0, 0);
        List<Movie> moviesLikedBySamuel = movieRepository.findMoviesLikedBy("0004");

        assertThat(recommendationsForSamuel.isEmpty(), is(false));
        assertThat(moviesLikedBySamuel, not(containsInAnyOrder(recommendationsForSamuel)));
    }

    @Test
    @DirtiesContext
    public void testGetRecommendationForUserResultsAreOrderedByNumberOfUsers() {
        User samuel = new User();
        User christian = new User();
        User clarissa = new User();

        Movie goneGirl = new Movie();
        Movie guardians = new Movie();
        Movie goneWithTheWind = new Movie();

        movieRepository.save(Arrays.asList(goneGirl, guardians, goneWithTheWind));

        samuel.setUsername("0004");
        christian.setUsername("0008");
        clarissa.setUsername("0016");
        samuel.setMoviesLiked(new HashSet<>(Arrays.asList(guardians)));
        christian.setMoviesLiked(new HashSet<>(Arrays.asList(guardians, goneGirl, goneWithTheWind)));
        clarissa.setMoviesLiked(new HashSet<>(Arrays.asList(guardians, goneWithTheWind)));

        userRepository.save(Arrays.asList(samuel, christian, clarissa));

        List<Movie> recommendationsForSamuel = movieRepository.findRecommendationsFor("0004", 0, 0);

        assertThat(recommendationsForSamuel.size(), equalTo(2));
        assertThat(recommendationsForSamuel.get(0), equalTo(goneWithTheWind));
        assertThat(recommendationsForSamuel.get(1), equalTo(goneGirl));
    }
}
