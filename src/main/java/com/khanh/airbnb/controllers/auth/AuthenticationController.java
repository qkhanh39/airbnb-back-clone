package com.khanh.airbnb.controllers.auth;

import com.khanh.airbnb.domain.entities.UserEntity;
import com.khanh.airbnb.dto.registration.RegistrationRequestDto;
import com.khanh.airbnb.dto.registration.RegistrationResponseDto;
import com.khanh.airbnb.mappers.RegistrationMapper;
import com.khanh.airbnb.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthService authService;
    private final RegistrationMapper registrationMapper;

    public AuthenticationController(AuthService authService, RegistrationMapper registrationMapper){
        this.authService = authService;
        this.registrationMapper = registrationMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponseDto> createNewUser(@Valid @RequestBody RegistrationRequestDto registrationRequestDto){
        UserEntity userEntity = authService.registerUser(registrationRequestDto);
        RegistrationResponseDto registrationResponseDto = registrationMapper.toDto(userEntity);
        return new ResponseEntity<>(registrationResponseDto, HttpStatus.OK);
    }
}
