package com.movie.me.repository;

import com.movie.me.beans.Neo4jTestConfiguration;
import com.movie.me.domain.Movie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Neo4jTestConfiguration.class)
@ActiveProfiles(profiles = "test")
public class MovieRepositoryTest {
    @Autowired
    MovieRepository movieRepository;

    private Movie newHope;

    @Before
    public void setup() {
        newHope = movieRepository.findByImdbid("tt0076759");
    }

    @Test
    @DirtiesContext
    public void testFindByExistentActors() {
        List<Movie> result = movieRepository.findByActorLike("Harrison Ford", 0, 10);

        assertThat(result, hasItems(newHope));
    }

    @Test
    @DirtiesContext
    public void testFindByNonexistentActors() {
        List<Movie> result = movieRepository.findByActorLike("Harrison Chevy", 1, 10);

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    @DirtiesContext
    public void testFindByActorsCaseInsensitive() {
        List<Movie> lowerCase = movieRepository.findByActorLike("harrison ford", 1, 10);
        List<Movie> upperCase = movieRepository.findByActorLike("HARRISON FORD", 1, 10);

        assertThat(lowerCase, equalTo(upperCase));
    }
}
