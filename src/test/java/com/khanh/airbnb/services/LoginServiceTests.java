package com.khanh.airbnb.services;

import com.khanh.airbnb.domain.entities.UserEntity;
import com.khanh.airbnb.dto.login.LoginRequestDto;
import com.khanh.airbnb.exceptions.AuthenticationErrorException;
import com.khanh.airbnb.repositories.UserRepository;
import com.khanh.airbnb.services.impl.LoginServiceImpl;
import com.khanh.airbnb.util.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTests {
    @Mock
    private UserRepository userRepository;

    @Mock
    private EncryptionService encryptionService;

    @Mock
    private JWTService jwtService;

    @InjectMocks
    private LoginServiceImpl loginService;

    @Test
    void testLoginUserReturnJWTTokenWhenSuccess() {
        LoginRequestDto loginRequest = new LoginRequestDto("test-user", "123456");
        UserEntity user = new UserEntity();
        user.setUsername("test-user");
        user.setPassword("hashedPassword");
        when(userRepository.findByUsername("test-user")).thenReturn(Optional.of(user));

        when(encryptionService.verifyPassword(loginRequest.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtService.generateJWT(user)).thenReturn("fake-jwt-token");

        String jwt = loginService.loginUser(loginRequest);
        assertEquals("fake-jwt-token", jwt);
        verify(userRepository).findByUsername(loginRequest.getUsername());
        verify(encryptionService).verifyPassword(loginRequest.getPassword(), user.getPassword());
        verify(jwtService).generateJWT(user);
    }

    @Test
    void testLoginUserThrowsExceptionWhenUsernameNotFound() {
        LoginRequestDto loginRequestDto = new LoginRequestDto("test-user", "password");
        when(userRepository.findByUsername(loginRequestDto.getUsername())).thenReturn(Optional.empty());

        assertThrows(AuthenticationErrorException.class, () -> loginService.loginUser(loginRequestDto));
        verify(userRepository).findByUsername("test-user");
        verifyNoMoreInteractions(encryptionService, jwtService);
    }

    @Test
    void testLoginUserThrowsExceptionWhenWrongPassword() {
        LoginRequestDto loginRequestDto = new LoginRequestDto("test-user", "password");
        UserEntity user = new UserEntity();
        user.setUsername("test-user");
        user.setPassword("hashedPassword");

        when(userRepository.findByUsername(loginRequestDto.getUsername())).thenReturn(Optional.of(user));
        when(encryptionService.verifyPassword(loginRequestDto.getPassword(), user.getPassword())).thenReturn(false);

        assertThrows(AuthenticationErrorException.class, () -> loginService.loginUser(loginRequestDto));
        verify(encryptionService).verifyPassword(loginRequestDto.getPassword(), user.getPassword());
        verify(jwtService, never()).generateJWT(any());
    }
}
