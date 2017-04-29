package com.movie.me.service;

import org.apache.commons.validator.EmailValidator;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class InputUtil {

    public Boolean validEmail(String email) {
        return email != null
                && !email.isEmpty()
                && EmailValidator.getInstance().isValid(email);
    }

    public Boolean validUsername(String username) {
        return username != null
                && !username.isEmpty();
    }

    public Boolean validImdbid(String imdbid) {
        Pattern pattern = Pattern.compile("tt\\d{7}");
        Matcher matcher = pattern.matcher(imdbid);
        return matcher.find();
    }

    public Boolean validPassword(String password, String check) {
        return password != null
                && !password.isEmpty()
                && check.equals(password);
    }
}
