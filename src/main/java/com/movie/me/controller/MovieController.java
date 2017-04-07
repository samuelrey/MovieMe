package com.movie.me.controller;

import com.movie.me.domain.Movie;
import com.movie.me.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        if( title != null ) {
            return movieService.findByTitleLike(title);
        }

        return new ArrayList<>();
    }

    @RequestMapping(value="/movie/search", method=RequestMethod.GET, produces="application/json")
    public List<Movie> searchForMovie(@RequestParam(value="property") String property, @RequestParam(value="value") String value) {
        switch(property) {
            case "title":
                return movieService.findByTitleLike(value);
            case "rated":
                return movieService.findByRated(value);
            case "released":
                return movieService.findByReleaseDate(value);
            case "actor":
                return movieService.findByActorLike(value);
            case "director":
                return movieService.findByDirectorLike(value);
            case "writer":
                return movieService.findByWriterLike(value);
            case "genre":
                return movieService.findByGenreLike(value);
            default:
                return new ArrayList<>();
        }
    }
}
