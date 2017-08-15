package com.movie.me.security;

import com.movie.me.domain.models.User;

public final class JwtUserFactory {

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        );
    }
}
