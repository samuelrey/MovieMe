package com.movie.me.repository;

import com.movie.me.beans.Neo4jTestConfiguration;
import com.movie.me.domain.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Neo4jTestConfiguration.class)
@ActiveProfiles(profiles = "test")
public class MovieRepositoryTest {
    @Autowired
    MovieRepository movieRepository;

    // -- findByImdbid -------------------------------------------------------------------------------------------------
    @Test
    public void testFindByImdbidValid() {
        Movie expected = new Movie.MovieBuilder()
                .withTitle("Star Wars: Episode IV - A New Hope")
                .withRated("PG")
                .withActors("Mark Hamill, Harrison Ford, Carrie Fisher, Peter Cushing")
                .withDirector("George Lucas")
                .withImdbid("tt0076759")
                .withRating(8.7)
                .withGenre("Action, Adventure, Fantasy")
                .withRuntime(121)
                .withWriter("George Lucas")
                .withReleaseDate(233366400)
                .withPoster("https://images-na.ssl-images-amazon.com/images/M/MV5BYzQ2OTk4N2QtOGQwNy00MmI3LWEwNmEtOTk0O"
                        + "TY3NDk2MGJkL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyNjc1NTYyMjg@._V1_SX300.jpg")
                .withPlot("Luke Skywalker joins forces with a Jedi Knight, a cocky pilot, a wookiee and two droids to "
                        + "save the galaxy from the Empire's world-destroying battle-station, while also attempting to "
                        + "rescue Princess Leia from the evil Darth Vader.")
                .build();

        Movie actual = movieRepository.findByImdbid("tt0076759");

        System.out.println(actual.equals(expected));
        assertThat(actual, equalTo(expected));
    }

    @Test
    public void testFindByImdbidInvalid() {
        Movie actual = movieRepository.findByImdbid("");

        assertThat(actual, equalTo(null));
    }

    // -- findByActors -------------------------------------------------------------------------------------------------
    @Test
    public void testFindByActorExist() {
        List<Movie> expected = new ArrayList<>();
        String[] imdbids = {"tt0076759", "tt0086190", "tt0083658", "tt0082971", "tt0087469", "tt0097576", "tt0367882",
                "tt0080684", "tt0096463", "tt0118972"};
        for(String imdbid: imdbids) {
            expected.add(movieRepository.findByImdbid(imdbid));
        }

        List<Movie> actual = movieRepository.findByActorLike("Harrison Ford", 0, 10);

        assertThat(actual, containsInAnyOrder(expected.toArray()));
    }

    @Test
    public void testFindByActorNotExist() {
        List<Movie> actual = movieRepository.findByActorLike("Harrison Chevy", 0, 10);

        assertThat(actual.isEmpty(), is(true));
    }

    @Test
    public void testFindByActorCaseInsensitive() {
        List<Movie> lowerCase = movieRepository.findByActorLike("harrison ford", 0, 10);
        List<Movie> upperCase = movieRepository.findByActorLike("HARRISON FORD", 0, 10);

        assertThat(lowerCase, equalTo(upperCase));
    }

    @Test
    public void testFindByActorNextPageUniqueMovies() {
        List<Movie> pageOne = movieRepository.findByActorLike("Harrison Ford", 0, 10);
        List<Movie> pageTwo = movieRepository.findByActorLike("Harrison Ford", 10, 10);

        assertThat(pageOne, not(containsInAnyOrder(pageTwo.toArray())));
    }

    // -- findByTitle --------------------------------------------------------------------------------------------------
    @Test
    public void testFindByTitleExist() {
        List<Movie> expected = new ArrayList<>();
        String[] imdbids = {"tt0076759", "tt0120915", "tt0086190", "tt0121765", "tt0080684", "tt0121766", "tt1185834",
                "tt0193524", "tt0457489"};
        for(String imdbid: imdbids) {
            expected.add(movieRepository.findByImdbid(imdbid));
        }

        List<Movie> actual = movieRepository.findByTitleLike("Star Wars", 0, 10);

        assertThat(actual, containsInAnyOrder(expected.toArray()));
    }

    @Test
    public void testFindByTitleNotExist() {
        List<Movie> actual = movieRepository.findByTitleLike("Star Walrus", 0, 10);

        assertThat(actual.isEmpty(), is(true));
    }

    @Test
    public void testFindByTitleCaseInsensitive() {
        List<Movie> lowerCase = movieRepository.findByTitleLike("star wars", 0, 10);
        List<Movie> upperCase = movieRepository.findByTitleLike("STAR WARS", 0, 10);

        assertThat(lowerCase, equalTo(upperCase));
    }

    @Test
    public void testFindByTitleNextPageUniqueMovies() {
        List<Movie> pageOne = movieRepository.findByTitleLike("Star Wars", 0, 10);
        List<Movie> pageTwo = movieRepository.findByTitleLike("Star Wars", 10, 10);

        assertThat(pageOne, not(containsInAnyOrder(pageTwo.toArray())));
    }

    @Test
    public void testFindByTitleNoMoreElementsThanPageSize() {
        List<Movie> actual = movieRepository.findByTitleLike("the", 0, 10);

        assertThat(actual.size(), lessThanOrEqualTo(10));
    }

    // -- findByWriter -------------------------------------------------------------------------------------------------
    @Test
    public void testFindByWriterExist() {
        List<Movie> expected = new ArrayList<>();
        String[] imdbids = {"tt0076759", "tt0120915", "tt0086190", "tt0121765", "tt0082971", "tt0087469", "tt0097576",
                "tt0367882", "tt0066434", "tt0069704"};
        for(String imdbid: imdbids) {
            expected.add(movieRepository.findByImdbid(imdbid));
        }

        List<Movie> actual = movieRepository.findByWriterLike("George Lucas", 0, 10);

        assertThat(actual, containsInAnyOrder(expected.toArray()));
    }

    @Test
    public void testFindByWriterNotExist() {
        List<Movie> actual = movieRepository.findByWriterLike("George Lucha Libre", 0, 10);

        assertThat(actual.isEmpty(), is(true));
    }

    @Test
    public void testFindByWriterCaseInsensitive() {
        List<Movie> lowerCase = movieRepository.findByWriterLike("GEORGE LUCAS", 0, 10);
        List<Movie> upperCase = movieRepository.findByWriterLike("george lucas", 0, 10);

        assertThat(lowerCase, equalTo(upperCase));
    }

    @Test
    public void testFindByWriterNextPageUniqueMovies() {
        List<Movie> pageOne = movieRepository.findByWriterLike("George Lucas", 0, 10);
        List<Movie> pageTwo = movieRepository.findByWriterLike("George Lucas", 10, 10);

        assertThat(pageOne, not(containsInAnyOrder(pageTwo.toArray())));
    }

    // -- findByGenre --------------------------------------------------------------------------------------------------
    @Test
    public void testFindByGenreExist() {
        List<Movie> expected = new ArrayList<>();
        String[] imdbids = {"tt0083866", "tt0116629", "tt0133093", "tt0234215", "tt0242653", "tt1104001", "tt0092007",
                "tt0120844", "tt0088170", "tt0079945"};
        for(String imdbid: imdbids) {
            expected.add(movieRepository.findByImdbid(imdbid));
        }

        List<Movie> actual = movieRepository.findByGenreLike("Sci-Fi", 0, 10);

        assertThat(actual, containsInAnyOrder(expected.toArray()));
    }

    @Test
    public void testFindByGenreNotExist() {
        List<Movie> actual = movieRepository.findByGenreLike("WiFi", 0, 10);

        assertThat(actual.isEmpty(), is(true));
    }

    @Test
    public void testFindByGenreCaseInsensitive() {
        List<Movie> lowerCase = movieRepository.findByGenreLike("sci-fi", 0, 10);
        List<Movie> upperCase = movieRepository.findByGenreLike("SCI-FI", 0, 10);

        assertThat(lowerCase, equalTo(upperCase));
    }

    @Test
    public void testFindByGenreNextPageUniqueMovies() {
        List<Movie> pageOne = movieRepository.findByGenreLike("Sci-Fi", 0, 10);
        List<Movie> pageTwo = movieRepository.findByGenreLike("Sci-Fi", 10, 10);

        assertThat(pageOne, not(containsInAnyOrder(pageTwo.toArray())));
    }

    // -- findByDirector -----------------------------------------------------------------------------------------------
    @Test
    public void testFindByDirectorExist() {
        List<Movie> expected = new ArrayList<>();
        String[] imdbids = {"tt0076759", "tt0120915", "tt0121765", "tt0066434", "tt0069704", "tt0121766"};
        for(String imdbid: imdbids) {
            expected.add(movieRepository.findByImdbid(imdbid));
        }

        List<Movie> actual = movieRepository.findByDirectorLike("George Lucas", 0, 10);

        assertThat(actual, containsInAnyOrder(expected.toArray()));
    }

    @Test
    public void testFindByDirectorNotExist() {
        List<Movie> actual = movieRepository.findByDirectorLike("George Lucha Libre", 0, 10);

        assertThat(actual.isEmpty(), is(true));
    }

    @Test
    public void testFindByDirectorCaseInsensitive() {
        List<Movie> lowerCase = movieRepository.findByDirectorLike("GEORGE LUCAS", 0, 10);
        List<Movie> upperCase = movieRepository.findByDirectorLike("george lucas", 0, 10);

        assertThat(lowerCase, equalTo(upperCase));
    }

    @Test
    public void testFindByDirectorNextPageUniqueMovies() {
        List<Movie> pageOne = movieRepository.findByDirectorLike("George Lucas", 0, 10);
        List<Movie> pageTwo = movieRepository.findByDirectorLike("George Lucas", 10, 10);

        assertThat(pageOne, not(containsInAnyOrder(pageTwo.toArray())));
    }
}
