package com.khanh.airbnb.controllers.auth;

import com.khanh.airbnb.dto.login.LoginRequestDto;
import com.khanh.airbnb.dto.login.LoginResponseDto;
import com.khanh.airbnb.services.LoginService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        String jwt = loginService.loginUser(loginRequestDto);
        LoginResponseDto responseDto = new LoginResponseDto();
        responseDto.setJwt(jwt);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
