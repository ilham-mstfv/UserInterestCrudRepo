package com.example.userinterestcrudrepo.services;

import com.auth0.jwt.exceptions.JWTVerificationException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Service
public class JwtFilterService extends OncePerRequestFilter {

    private final PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    private UserAccDetailsService userAccDetailsService;

    public JwtFilterService(
            JwtService jwtService,
            UserAccDetailsService userAccDetailsService,
            PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userAccDetailsService = userAccDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException, JWTVerificationException {

        if (request.getRequestURI().equals("/users/auth")) {
            chain.doFilter(request, response);
            return;
        }
        Optional.ofNullable(request.getHeader("Authorization"))
                .filter(authHeader -> !authHeader.isBlank() && authHeader.startsWith("Bearer "))
                .map(authHeader -> authHeader.substring(7))
                .ifPresentOrElse(
                        token -> {
                            String username = jwtService.validateTokenAndRetrieveSubject(token);

                            UserDetails userDetails = userAccDetailsService.loadUserByUsername(username);

                            UsernamePasswordAuthenticationToken authToken =
                                    new UsernamePasswordAuthenticationToken(
                                            userDetails,
                                            null,
                                            userDetails.getAuthorities());

                            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authToken);

                        }, () -> { throw new RuntimeException("Invalid token"); });
        chain.doFilter(request, response);
    }
}
