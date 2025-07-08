package com.khanh.airbnb.services;

import com.khanh.airbnb.domain.entities.UserEntity;

public interface UserService {
    UserEntity partialUpdate(Long id, UserEntity userEntity);
}
