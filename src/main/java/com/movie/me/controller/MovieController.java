package com.movie.me.controller;

import com.movie.me.domain.models.Movie;
import com.movie.me.domain.exceptions.MovieDoesNotExistException;
import com.movie.me.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Defines Http endpoints for Movie related resources.
 *
 * @author  Samuel Villavicencio
 * @since   03-05-17
 */
@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    /**
     * Finds a unique movie based on imdbid.
     * @param imdbid    Unique identifier for Movie.
     * @return  Movie with given imdbid.
     * @throws  MovieDoesNotExistException  There is no Movie with the given imdbid.
     */
    @RequestMapping(value="{imdbid}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public Movie getMovie(@PathVariable(value="imdbid") String imdbid) throws MovieDoesNotExistException {
        return movieService.findByImdbid(imdbid);
    }

    /**
     * Finds some number of Movies that have a similar title to that requested.
     * @param title Pattern to compare to Movie titles.
     * @param page  Nth page of the entire set of results.
     * @param size  Number of Movies per page.
     * @return  Movies with similar titles.
     */
    @RequestMapping(method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> findByTitleLike(@Param(value="title") String title, @Param(value="page") Integer page,
                                       @Param(value="size") Integer size) {
        return movieService.findByTitleLike(title, page, size);
    }
}
