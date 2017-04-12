package com.movie.me.repository;

import java.util.List;
import java.util.Arrays;

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
public class UserFindByNameLikeIT {
    @Autowired
    private UserRepository userRepository;

    private User samuel, sam;
    @Before
    public void initialize() {
        samuel = new User();
        samuel.setUsername("sammy123");
        samuel.setName("Samuel Villavicencio");
        samuel.setEmail("savillavicencio@csumb.edu");

        sam = new User();
        sam.setUsername("sam123");
        sam.setName("sam");
        sam.setEmail("svillavicencio@csumb.edu");

        userRepository.save(samuel);
        userRepository.save(sam);


        return;
    }

    @Test
    @DirtiesContext
    public void testFindByNameLikeCaseInsensitiveSearch() {
        List<User> lowerCase = userRepository.findByNameLike("samuel");
        List<User> upperCase = userRepository.findByNameLike("SAMUEL");

        assertThat(lowerCase, equalTo(upperCase));
    }

    @Test
    @DirtiesContext
    public void testFindByNameLikeEmptyResult() {
        List<User> result = userRepository.findByNameLike("so");
        assertThat(result.isEmpty(), is(true));
    }

    @Test
    @DirtiesContext
    public void testFindByNameLikeSuccessfulResult() {
        List<User> result = userRepository.findByNameLike("sam");
        assertThat(result, containsInAnyOrder(samuel, sam));
    }


}
