package com.example.userinterestcrudrepo.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private final static int EXPIRATION_TIME = 86_400_000; // One Day
    private final static String ISSUER = "UserInterestCrudRepo";

    public String generateToken(UserDetails userDetails)
            throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                .withSubject("username")
                .withClaim("username", userDetails.getUsername())
                .withIssuer(ISSUER)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveSubject(String token)
            throws JWTVerificationException {
        return JWT.require(Algorithm.HMAC256(secret))
                .withSubject("username")
                .withIssuer(ISSUER)
                .build()
                .verify(token)
                .getClaim("username").asString();
    }

    public String validateTokenWithUsername(String token, String username)
            throws JWTVerificationException{

        return Optional.of(JWT.require(Algorithm.HMAC256(secret))
                        .withIssuer(ISSUER)
                        .build().verify(token))
                .filter(jwt -> jwt.getExpiresAt() == null || jwt.getExpiresAt().after(new Date()))
                .filter(jwt -> username.equals(jwt.getClaim("username").asString()))
                .map(DecodedJWT::getToken)
                .orElseThrow(() ->
                        new JWTVerificationException(
                                "Token is invalid or does not match username"));
    }
}
