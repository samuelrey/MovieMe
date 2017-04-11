package com.movie.me.service;

import com.movie.me.domain.Movie;

import java.util.List;

public interface MovieService {
    Movie getMovie(String imdbid);
    List<Movie> searchMovies(String query);
    List<Movie> findByTitleLike(String title);
    List<Movie> findByRated(String rated);
    List<Movie> findByReleaseDate(String released);
    List<Movie> findByActorLike(String actor);
    List<Movie> findByWriterLike(String writer);
    List<Movie> findByDirectorLike(String director);
    List<Movie> findByGenreLike(String genre);
}
