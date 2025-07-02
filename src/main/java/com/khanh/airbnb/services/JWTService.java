package com.khanh.airbnb.services;

import com.khanh.airbnb.domain.entities.UserEntity;

public interface JWTService {
    String generateJWT(UserEntity user);
    String getUsername(String token);

    void validateToken(String token);
}
