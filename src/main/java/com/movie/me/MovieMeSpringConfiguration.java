package com.movie.me;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class MovieMeSpringConfiguration {
	public static void main(String[] args) throws Exception {
        SpringApplication.run(MovieMeSpringConfiguration.class, args);
    }
}
