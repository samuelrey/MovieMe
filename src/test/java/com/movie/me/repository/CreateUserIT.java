package com.movie.me.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.junit.Before;

import com.movie.me.domain.User;
import com.movie.me.beans.Neo4jTestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Neo4jTestConfiguration.class)
@ActiveProfiles(profiles = "test")
public class CreateUserIT {
    @Autowired
    private UserRepository userRepository;

    private User sam;
    @Before
    public void initialize() {
        sam = new User();
        sam.setUsername("sam123");
        sam.setEmail("svillavicencio@csumb.edu");

        userRepository.save(sam);


        return;
    }

    @Test
    @DirtiesContext
    public void createUserSuccess() {
        User samuel = new User();
        samuel.setPhoto("");
        samuel.setEmail("savillavicencio@csumb.edu");

        User result = userRepository.createUser(samuel.getEmail(), samuel.getUsername(), samuel.getPhoto(), "");
        assertThat(result.equals(samuel), is(true));
    }

}
