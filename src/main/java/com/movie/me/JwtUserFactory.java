package com.movie.me;

import com.movie.me.domain.User;

public final class JwtUserFactory {

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        );
    }
}
