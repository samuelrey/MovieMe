package com.movie.me.beans;

import com.movie.me.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBeanConfiguration {

    @Bean
    public UserService getUserService() {
        return new UserServiceImpl();
    }

    @Bean
    public MovieService getMovieService() {
        return new MovieServiceImpl();
    }

    @Bean
    public InputUtil getInputValidatorService() {
        return new InputUtil();
    }
}
