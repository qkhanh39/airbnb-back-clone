package com.khanh.airbnb.services;

import com.khanh.airbnb.domain.entities.UserEntity;
import com.khanh.airbnb.dto.registration.RegistrationRequestDto;
import com.khanh.airbnb.exceptions.EmailAlreadyExistException;
import com.khanh.airbnb.exceptions.UsernameAlreadyExistException;

public interface AuthService {
    UserEntity registerUser(RegistrationRequestDto registrationRequestDto);
}
