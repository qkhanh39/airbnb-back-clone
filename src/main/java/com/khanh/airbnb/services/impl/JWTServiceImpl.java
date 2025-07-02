package com.khanh.airbnb.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.khanh.airbnb.domain.entities.UserEntity;
import com.khanh.airbnb.exceptions.AuthenticationErrorException;
import com.khanh.airbnb.services.JWTService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTServiceImpl implements JWTService {

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiryInSeconds}")
    private int expiryInSeconds;

    private Algorithm algorithm;

    private static final String USERNAME_KEY = "USERNAME";
    @PostConstruct
    public void postConstruct() {
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    @Override
    public String generateJWT(UserEntity user) {
        return JWT.create()
                .withClaim(USERNAME_KEY, user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000L * expiryInSeconds)))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    @Override
    public String getUsername(String token) {
        return JWT.decode(token).getClaim(USERNAME_KEY).asString();
    }

    @Override
    public void validateToken(String token) {
        try {
            JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build()
                    .verify(token);
        } catch (JWTVerificationException ex) {
            throw  new AuthenticationErrorException("Invalid or expired token");
        }
    }
}
