package com.movie.me.repository;

import com.movie.me.domain.Movie;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends GraphRepository<Movie> {
    @Query("MATCH (m:Movie {imdbid:{imdbid}}) " +
            "RETURN m")
    Movie findByImdbid(@Param("imdbid") String imdbid);

    @Query("MATCH (m:Movie) " +
            "WHERE m.title =~ ('(?i).*'+{title}+'.*') " +
            "RETURN m SKIP {page} LIMIT {size}")
    List<Movie> findByTitleLike(@Param("title") String title, @Param("page") int page, @Param("size") int size);

    @Query("MATCH (m:Movie) " +
            "WHERE m.actors =~ ('(?i).*'+{actor}+'.*') " +
            "RETURN m SKIP {page} LIMIT {size}")
    List<Movie> findByActorLike(@Param("actor") String actor, @Param("page") int page, @Param("size") int size);

    @Query("MATCH (m:Movie) " +
            "WHERE m.writer =~ ('(?i).*'+{writer}+'.*') " +
            "RETURN m SKIP {page} LIMIT {size}")
    List<Movie> findByWriterLike(@Param("writer") String writer, @Param("page") int page, @Param("size") int size);

    @Query("MATCH (m:Movie) " +
            "WHERE m.director =~ ('(?i).*'+{director}+'.*') " +
            "RETURN m SKIP {page} LIMIT {size}")
    List<Movie> findByDirectorLike(@Param("director") String director, @Param("page") int page,
                                   @Param("size") int size);

    @Query("MATCH (m:Movie) " +
            "WHERE m.genre =~ ('(?i).*'+{genre}+'.*') " +
            "RETURN m SKIP {page} LIMIT {size}")
    List<Movie> findByGenreLike(@Param("genre") String genre, @Param("page") int page, @Param("size") int size);

    @Query("MATCH (:User {username:{username}}) " +
            "-[:likes]->(m:Movie) " +
            "RETURN m")
    List<Movie> findMoviesLikedBy(@Param("username") String username);

    @Query("MATCH(u:User {email:{username}})" +
            "-[:likes]->(:Movie)<-[:likes]-(:User)" +
            "-[:likes]->(m:Movie) " +
            "WHERE NOT (u)-[:likes]->(m) " +
            "WITH m, COUNT(m) AS hits " +
            "RETURN m ORDER BY hits DESC " +
            "SKIP {page} LIMIT {size}")
    List<Movie> findRecommendationsFor(@Param("username") String username, @Param("page") int page, @Param("size") int size);

    @Query("MATCH (u:User {username:{username}}), " +
            "(m:Movie {imdbid:{imdbid}}) " +
            "MERGE (u)-[:likes]->(m) " +
            "RETURN m")
    Movie addUserLikesMovie(@Param("username") String username, @Param("imdbid") String imdbid);

    @Query("MATCH (u:User {username:{username}})-[l:likes]-(m:Movie {imdbid:{imdbid}}) " +
            "DELETE l RETURN m")
    Movie removeUserLikesMovie(@Param("username") String username, @Param("imdbid") String imdbid);
}
