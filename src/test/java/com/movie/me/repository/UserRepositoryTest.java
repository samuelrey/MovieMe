package com.movie.me.repository;

import com.movie.me.beans.Neo4jTestConfiguration;
import com.movie.me.domain.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Neo4jTestConfiguration.class)
@ActiveProfiles(profiles = "test")
@DirtiesContext
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    // -- createUser ---------------------------------------------------------------------------------------------------
    @Test
    public void testCreateUserSuccess() {
        User expected = new User.UserBuilder()
                .withEmail("hansol0@example.com")
                .withUsername("hansol0")
                .withPhoto("https://cdn.theatlantic.com/assets/media/img/mt/2016/01/solo/lead_large.jpg?1452549010")
                .build();

        User actual = userRepository.createUser("hansol0@example.com", "hansol0", "https://cdn.theatlantic.com/assets" +
                "/media/img/mt/2016/01/solo/lead_large.jpg?1452549010", "nevertellmetheodds");

        assertThat(actual, equalTo(expected));
    }

    // -- findByUsername -----------------------------------------------------------------------------------------------
    @Test
    public void testFindByUsernameExist() {
        User expected = new User.UserBuilder()
                .withEmail("hansol0@example.com")
                .withUsername("hansol0")
                .withPhoto("https://cdn.theatlantic.com/assets/media/img/mt/2016/01/solo/lead_large.jpg?1452549010")
                .build();
        userRepository.save(expected);

        User actual = userRepository.findByUsername("hansol0");

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void testFindByUsernameNotExist() {
        User actual = userRepository.findByUsername("chewi3");

        assertThat(actual, equalTo(null));
    }

    // -- findByEmail --------------------------------------------------------------------------------------------------
    @Test
    public void testFindByEmailExist() {
        User expected = new User.UserBuilder()
                .withEmail("hansol0@example.com")
                .withUsername("hansol0")
                .withPhoto("https://cdn.theatlantic.com/assets/media/img/mt/2016/01/solo/lead_large.jpg?1452549010")
                .build();
        userRepository.save(expected);

        User actual = userRepository.findByEmail("hansol0@example.com");

        assertThat(actual, equalTo(expected));
    }

    @Test
    public void testFindByEmailNotExist() {
        User actual = userRepository.findByEmail("chewi3");

        assertThat(actual, equalTo(null));
    }
}
