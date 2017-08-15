package com.movie.me.service;

import com.movie.me.domain.models.Movie;
import com.movie.me.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie findByImdbid(String imdbid) {
        return movieRepository.findByImdbid(imdbid);
    }

    public List<Movie> findByTitleLike(String title, int page, int size) {
        return movieRepository.findByTitleLike(title, page, size);
    }
}
