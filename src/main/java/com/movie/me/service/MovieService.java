package com.movie.me.service;

import com.movie.me.domain.models.Movie;

import java.util.List;

public interface MovieService {

    Movie findByImdbid(String imdbid);

    List<Movie> findByTitleLike(String title, int page, int size);
}
