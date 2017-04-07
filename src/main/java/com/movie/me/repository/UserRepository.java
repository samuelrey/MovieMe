package com.movie.me.repository;

import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.movie.me.domain.User;
import com.movie.me.domain.Movie;

@Repository
public interface UserRepository extends GraphRepository<User> {
    @Query("MATCH (u:User {userid:{userid}} " +
            "RETURN u")
    User findByUserId(@Param("userid") String userid);

    @Query("CREATE (u:User {name:{name}, age:{age}, email:{email}, userid:{userid}, photo_uri:{photoURI}}) " +
            "RETURN u")
    User createUserNode(@Param("name") String name, @Param("age") String age, @Param("email") String email, @Param("userid") String userid, @Param("photoURI") String photoURI);

    @Query("MATCH (u:User) " +
            "WHERE u.name =~ ('(?i).*'+{name}+'.*')" +
            "RETURN u")
    List<User> findByNameLike(@Param("name") String name);

/*
    @Query("MATCH (u1:USER {USERID:{userid1}}), " + 
    	   "(u2:USER {USERID:{userid2}}) " + 
    	   "MERGE (u1)-[:FRIENDS]->(u2) " +
    	   "RETURN u2")
    User addUserFriendsUser(@Param("userid1") String userid1, @Param("userid2") String userid2);

    @Query("MATCH (u1:USER {USERID:{userid1}})-[f:FRIENDS]-(u2:USER {USERID:{userid2}}) " +
           "DELETE f return u2")
    User userRemovesFriend(@Param("userid1") String userid1, @Param("userid2") String userid2);
    
    @Query("MATCH (:USER {USERID:{userid}}) " +
            "-[:FRIENDS]->(u:USER) " +
            "RETURN u")
    List<User> retrieveFriendsOf(@Param("userid") String userid);
*/
}
