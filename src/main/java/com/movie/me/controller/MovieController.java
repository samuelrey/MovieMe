package com.movie.me.controller;

import com.movie.me.domain.Movie;
import com.movie.me.domain.MovieDoesNotExistException;
import com.movie.me.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @RequestMapping(value="{imdbid}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public Movie getMovie(@PathVariable String imdbid) throws MovieDoesNotExistException {
        return movieService.getMovie(imdbid);
    }

    @RequestMapping(method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> searchMovies(@RequestParam(value="query", required=false) String query)
            throws IllegalArgumentException {
        return movieService.searchMovies(query);
    }
}
