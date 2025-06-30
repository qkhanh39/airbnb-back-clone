package com.khanh.airbnb.services;

import com.khanh.airbnb.domain.entities.UserEntity;
import com.khanh.airbnb.dto.registration.RegistrationRequestDto;
import com.khanh.airbnb.exceptions.EmailAlreadyExistException;
import com.khanh.airbnb.exceptions.UsernameAlreadyExistException;
import com.khanh.airbnb.repositories.UserRepository;
import com.khanh.airbnb.services.impl.AuthServiceImpl;
import com.khanh.airbnb.util.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {
    @Mock
    private UserRepository userRepository;

    @Mock
    private EncryptionService encryptionService;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void testRegisterUserSuccessful() {
        RegistrationRequestDto requestDto = TestDataUtil.createTestUserDto1();
        when(userRepository.findByUsername("user1")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("user1@example.com")).thenReturn(Optional.empty());
        when(encryptionService.encryptPassword("password1")).thenReturn("hashedPassword");

        UserEntity savedUser = new UserEntity();
        savedUser.setFullName("Test User One");
        savedUser.setPassword("hashedPassword");
        savedUser.setUsername("user1");
        savedUser.setEmail("user1@example.com");

        when(userRepository.save(any(UserEntity.class))).thenReturn(savedUser);

        UserEntity result = authService.registerUser(requestDto);

        assertNotNull(result);
        assertEquals("user1", result.getUsername());
        assertEquals("user1@example.com", result.getEmail());
        assertEquals("hashedPassword", result.getPassword());

        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void testRegisterUsernameExistsThrowsException() {
        RegistrationRequestDto requestDto = TestDataUtil.createTestUserDto1();
        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(new UserEntity()));

        assertThrows(UsernameAlreadyExistException.class, () -> {
            authService.registerUser(requestDto);
        });
        verify(userRepository, never()).save(any());
    }

    @Test
    void testRegisterEmailExistsThrowsException() {
        RegistrationRequestDto requestDto = TestDataUtil.createTestUserDto1();
        when(userRepository.findByEmail("user1@example.com")).thenReturn(Optional.of(new UserEntity()));

        assertThrows(EmailAlreadyExistException.class, () -> {
            authService.registerUser(requestDto);
        });

        verify(userRepository, never()).save(any());
    }
}
