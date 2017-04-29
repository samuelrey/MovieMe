package com.movie.me.controller;

import com.movie.me.domain.Movie;
import com.movie.me.domain.MovieDoesNotExistException;
import com.movie.me.service.InputUtil;
import com.movie.me.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private InputUtil inputUtil;

    @Autowired
    private MovieService movieService;

    @RequestMapping(value="{imdbid}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public Movie getMovie(@PathVariable String imdbid) throws MovieDoesNotExistException {
        if( !inputUtil.validImdbid(imdbid) ) {
            throw new IllegalArgumentException(String.format("Invalid imdbid: %s", imdbid));
        }
        return movieService.findByImdbid(imdbid);
    }

    @RequestMapping(method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Movie> searchMovies(@RequestParam(value="query") String query) {
        return movieService.searchMovies(query);
    }
}
