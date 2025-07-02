package com.khanh.airbnb.services.impl;

import com.khanh.airbnb.domain.entities.UserEntity;
import com.khanh.airbnb.dto.login.LoginRequestDto;
import com.khanh.airbnb.exceptions.AuthenticationErrorException;
import com.khanh.airbnb.repositories.UserRepository;
import com.khanh.airbnb.services.EncryptionService;
import com.khanh.airbnb.services.JWTService;
import com.khanh.airbnb.services.LoginService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;
    private final EncryptionService encryptionService;
    private final JWTService jwtService;

    public LoginServiceImpl(UserRepository userRepository, EncryptionService encryptionService, JWTService jwtService){
        this.userRepository = userRepository;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
    }

    @Override
    public String loginUser(LoginRequestDto loginRequestDto) {
        Optional<UserEntity> foundUser = userRepository.findByUsername(loginRequestDto.getUsername());
        if (foundUser.isEmpty()){
            throw new AuthenticationErrorException("Incorrect username or password");
        }
        UserEntity user = foundUser.get();
        if (!encryptionService.verifyPassword(loginRequestDto.getPassword(), user.getPassword())) {
            throw new AuthenticationErrorException("Incorrect username or password");
        }

        return jwtService.generateJWT(user);
    }
}
