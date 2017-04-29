package com.movie.me.service;

import com.movie.me.domain.Movie;

import java.util.List;

public interface MovieService {

    Movie findByImdbid(String imdbid);

    List<Movie> searchMovies(String query);
}
