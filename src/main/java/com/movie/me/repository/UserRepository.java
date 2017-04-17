package com.movie.me.repository;

import com.movie.me.domain.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends GraphRepository<User> {
    @Query("CREATE (u:User {email:{email}, username:{username}, photo:{photo}, password:{password}}) " +
            "RETURN u")
    User createUser(@Param("email") String email,
                    @Param("username") String username,
                    @Param("photo") String photo,
                    @Param("password") String password);

    @Query("MATCH (u:User {username:{username}}) " +
            "RETURN u")
    User findByUsername(@Param("username") String username);

    @Query("MATCH (u:User {email:{email}}) " +
            "RETURN u")
    User findByEmail(@Param("email") String email);
}
