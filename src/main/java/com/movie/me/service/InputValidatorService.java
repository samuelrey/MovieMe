package com.movie.me.service;

import org.apache.commons.validator.EmailValidator;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class InputValidatorService {
    public boolean validEmail(String email) {
        return email != null
                && !email.isEmpty()
                && EmailValidator.getInstance().isValid(email);
    }

    public boolean validUsername(String username) {
        return username != null
                && !username.isEmpty();
    }

    public boolean validImdbid(String imdbid) {
        Pattern pattern = Pattern.compile("tt\\d{7}");
        Matcher matcher = pattern.matcher(imdbid);
        return matcher.find();
    }
}
