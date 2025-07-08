package com.khanh.airbnb.services.impl;

import com.khanh.airbnb.domain.entities.UserEntity;
import com.khanh.airbnb.repositories.UserRepository;
import com.khanh.airbnb.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity partialUpdate(Long id, UserEntity userEntity) {
        userEntity.setUserId(id);
        return userRepository.findById(id).map(existingUser -> {
            Optional.ofNullable(userEntity.getFullName()).ifPresent(existingUser::setFullName);
            Optional.ofNullable(userEntity.getEmail()).ifPresent(existingUser::setEmail);
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
