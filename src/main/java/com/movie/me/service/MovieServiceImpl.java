package com.movie.me.service;

import com.movie.me.domain.Movie;
import com.movie.me.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public Movie getMovie(String imdbid) {
        return movieRepository.getMovie(imdbid);
    }

    public List<Movie> findByTitleLike(String title) {
        if( title.length() > 0 ) {
            return movieRepository.findByTitleLike(title);
        }

        return new ArrayList<>();
    }

    public List<Movie> findByRated(String rated) {
        if( rated.equalsIgnoreCase("g") || rated.equalsIgnoreCase("pg") ||
            rated.equalsIgnoreCase("pg-13") || rated.equalsIgnoreCase("r") ||
            rated.equalsIgnoreCase("nc-17") ) {
            return movieRepository.findByRated(rated);
        }

        return new ArrayList<>();
    }

    public List<Movie> findByReleaseDate(String released) {
        if( released.length() > 0 ) {
            return movieRepository.findByReleaseDate(released);
        }

        return new ArrayList<>();
    }

    public List<Movie> findByActorLike(String actor) {
        if( actor.length() > 0 ) {
            return movieRepository.findByActorLike(actor);
        }

        return new ArrayList<>();
    }

    public List<Movie> findByWriterLike(String writer) {
        if( writer.length() > 0 ) {
            return movieRepository.findByWriterLike(writer);
        }

        return new ArrayList<>();
    }

    public List<Movie> findByDirectorLike(String director) {
        if( director.length() > 0 ) {
            return movieRepository.findByDirectorLike(director);
        }

        return new ArrayList<>();
    }

    public List<Movie> findByGenreLike(String genre) {
        if( genre.length() > 0 ) {
            return movieRepository.findByGenreLike(genre);
        }

        return new ArrayList<>();
    }

}
