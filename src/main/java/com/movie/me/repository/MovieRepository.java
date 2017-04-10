package com.movie.me.repository;

import com.movie.me.domain.Movie;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends GraphRepository<Movie> {
    @Query("MATCH (m:Movie {imdbid:{imdbid}}) " +
            "RETURN m")
    Movie getMovie(@Param("imdbid") String imdbid);

    @Query("MATCH (m:Movie) " +
            "WHERE m.title =~ ('(?i).*'+{title}+'.*') " +
            "RETURN m")
    List<Movie> findByTitleLike(@Param("title") String title);

    @Query("MATCH (m:Movie {rated:{rated}}) " +
            "RETURN m")
    List<Movie> findByRated(@Param("rated") String rated);

    @Query("MATCH (m:Movie {released:{released}}) " +
            "RETURN m")
    List<Movie> findByReleaseDate(@Param("released") String released);

    @Query("MATCH (m:Movie) " +
            "WHERE m.actors =~ ('(?i).*'+{actor}+'.*') " +
            "RETURN m")
    List<Movie> findByActorLike(@Param("actor") String actor);

    @Query("MATCH (m:Movie) " +
            "WHERE m.writer =~ ('(?i).*'+{writer}+'.*') " +
            "RETURN m")
    List<Movie> findByWriterLike(@Param("writer") String writer);

    @Query("MATCH (m:Movie) " +
            "WHERE m.director =~ ('(?i).*'+{director}+'.*') " +
            "RETURN m")
    List<Movie> findByDirectorLike(@Param("director") String director);

    @Query("MATCH (m:Movie) " +
            "WHERE m.genre =~ ('(?i).*'+{genre}+'.*') " +
            "RETURN m")
    List<Movie> findByGenreLike(@Param("genre") String genre);

    @Query("MATCH(m:Movie {imdbid:{imdbid}})" +
            "<-[l:likes]-() " +
            "RETURN COUNT(l)")
    int countLikesOf(@Param("imdbid") String imdbid);

    @Query("MATCH (:User {email:{userid}}) " +
            "-[:likes]->(m:Movie) " +
            "RETURN m")
    List<Movie> retrieveMoviesLikedBy(@Param("userid") String userid);

    @Query("MATCH(u:User {email:{userid}})" +
            "-[:likes]->(:Movie)<-[:likes]-(:User)" +
            "-[:likes]->(m:Movie) " +
            "WHERE NOT (u)-[:likes]->(m) " +
            "WITH m, COUNT(m) AS hits " +
            "RETURN m ORDER BY hits DESC " +
            "LIMIT 30")
    List<Movie> getRecommendationForUser(@Param("userid") String userid);

    @Query("MATCH (u:User {email:{userid}}), " +
            "(m:Movie {imdbid:{imdbid}}) " +
            "MERGE (u)-[:likes]->(m) " +
            "RETURN m")
    Movie addUserLikesMovie(@Param("userid") String userid,
                            @Param("imdbid") String imdbid);

    @Query("MATCH (u:User {email:{userid}})-[l:likes]-(m:Movie {imdbid:{imdbid}}) " +
            "DELETE l RETURN m")
    Movie userUnlikesMovie(@Param("userid") String userid,
                           @Param("imdbid") String imdbid);
}
