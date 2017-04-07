package com.movie.me.repository;

import com.movie.me.domain.Movie;

import com.movie.me.beans.Neo4jTestConfiguration;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;


import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Neo4jTestConfiguration.class)
@ActiveProfiles(profiles = "test")
public class MovieFindByImdbIdIT {
    @Autowired
    MovieRepository movieRepository;

    private Movie newHope;

    @Before
    public void initialize() {
        newHope = new Movie();
        newHope.setTitle("Star Wars: Episode IV - A New Hope");
        newHope.setImdbid("0004");

        movieRepository.save(newHope);
    }

    @Test
    @DirtiesContext
    public void testFindByImdbIdSuccessfulResult() {
        String imdbid = newHope.getImdbid();
        Movie result = movieRepository.getMovie(imdbid);

        assertThat(result.equals(newHope), is(true));
    }

    @Test
    @DirtiesContext
    public void testFindByImdbIdEmptyResult() {
        String imdbid = "0001";
        Movie result = movieRepository.getMovie(imdbid);

        assertThat(result, is(nullValue()));
    }

}
