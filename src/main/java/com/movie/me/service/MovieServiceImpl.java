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
        return movieRepository.findByImdbid(imdbid);
    }

    public List<Movie> searchMovies(String query) {
        return new ArrayList<>();
    }

    public List<Movie> findByTitleLike(String title) {
        if( title.length() > 0 ) {
            return movieRepository.findByTitleLike(title, 0, 0);
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
            return movieRepository.findByActorLike(actor, 1, 1);
        }

        return new ArrayList<>();
    }

    public List<Movie> findByWriterLike(String writer) {
        if( writer.length() > 0 ) {
            return movieRepository.findByWriterLike(writer, 0, 0);
        }

        return new ArrayList<>();
    }

    public List<Movie> findByDirectorLike(String director) {
        if( director.length() > 0 ) {
            return movieRepository.findByDirectorLike(director, 0, 0);
        }

        return new ArrayList<>();
    }

    public List<Movie> findByGenreLike(String genre) {
        if( genre.length() > 0 ) {
            return movieRepository.findByGenreLike(genre, 0, 0);
        }

        return new ArrayList<>();
    }

}
