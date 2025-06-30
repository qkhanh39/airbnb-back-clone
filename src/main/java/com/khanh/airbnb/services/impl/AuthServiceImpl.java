package com.khanh.airbnb.services.impl;

import com.khanh.airbnb.domain.entities.UserEntity;
import com.khanh.airbnb.dto.registration.RegistrationRequestDto;
import com.khanh.airbnb.exceptions.EmailAlreadyExistException;
import com.khanh.airbnb.exceptions.UsernameAlreadyExistException;
import com.khanh.airbnb.services.AuthService;
import com.khanh.airbnb.services.EncryptionService;
import org.springframework.stereotype.Service;
import com.khanh.airbnb.repositories.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final EncryptionService encryptionService;

    public AuthServiceImpl(UserRepository userRepository, EncryptionService encryptionService){
        this.userRepository = userRepository;
        this.encryptionService = encryptionService;
    }

    @Override
    public UserEntity registerUser(RegistrationRequestDto registrationRequestDto) {
        if (userRepository.findByUsername(registrationRequestDto.getUsername()).isPresent()){
            throw new UsernameAlreadyExistException("Username has been used");
        }
        if (userRepository.findByEmail(registrationRequestDto.getEmail()).isPresent()){
            throw new EmailAlreadyExistException("Email has been used");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(registrationRequestDto.getEmail());
        String encodedPassword = encryptionService.encryptPassword(registrationRequestDto.getPassword());
        userEntity.setPassword(encodedPassword);
        userEntity.setUsername(registrationRequestDto.getUsername());
        userEntity.setFullName(registrationRequestDto.getFullName());

        return userRepository.save(userEntity);
    }
}
