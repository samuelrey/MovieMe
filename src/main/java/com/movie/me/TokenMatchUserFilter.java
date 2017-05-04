package com.movie.me;

import com.movie.me.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenMatchUserFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Value("token")
    private String tokenHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authToken = request.getHeader(this.tokenHeader);
        String usernameFromToken = jwtTokenUtil.getUsernameFromToken(authToken);
        String usernameFromPath = null;
        String[] pathVariables = request.getServletPath().split("/");
        if( pathVariables.length > 1 && pathVariables[0].equals("user") && !pathVariables[1].equals("login")) {
            usernameFromPath = pathVariables[1];
        }

        if( usernameFromToken != null && SecurityContextHolder.getContext().getAuthentication() == null &&
                usernameFromToken.equals(usernameFromPath) ) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(usernameFromToken);

            if( jwtTokenUtil.validateToken(authToken, userDetails) ) {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        chain.doFilter(request, response);
    }
}
