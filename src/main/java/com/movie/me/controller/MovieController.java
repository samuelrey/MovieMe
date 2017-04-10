package com.movie.me.controller;

import com.movie.me.domain.Movie;
import com.movie.me.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @RequestMapping(value="{imdbid}", method=RequestMethod.GET, produces="application/json")
    public Movie getMovie(@PathVariable String imdbid) {
        return movieService.getMovie(imdbid);
    }

    @RequestMapping(method=RequestMethod.GET, produces="application/json")
    public List<Movie> searchMovies(@RequestParam(value="title", required=false) String title) {
        return movieService.findByTitleLike(title);
    }
}
