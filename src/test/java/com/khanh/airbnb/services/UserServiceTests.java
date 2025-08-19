package com.khanh.airbnb.services;

import com.khanh.airbnb.domain.entities.UserEntity;
import com.khanh.airbnb.exceptions.ResourceNotFoundException;
import com.khanh.airbnb.repositories.UserRepository;
import com.khanh.airbnb.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testThatUpdateUserServiceReturnsUpdatedUser() {
        UserEntity user = new UserEntity();
        user.setUserId(1L);
        user.setFullName("Old name");
        user.setEmail("old@email.com");

        UserEntity updateUser = new UserEntity();
        updateUser.setFullName("New name");

        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserEntity result = userService.partialUpdate(user.getUserId(), updateUser);

        assertEquals(user.getUserId(), result.getUserId());
        assertEquals(updateUser.getFullName(), result.getFullName());
        assertEquals(user.getEmail(), result.getEmail());

        verify(userRepository).findById(user.getUserId());
        verify(userRepository).save(user);
    }

    @Test
    public void testThatUpdateUserThrowsExceptionWhenFail() {
        UserEntity updateRequest = new UserEntity();
        updateRequest.setUserId(2L);
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.partialUpdate(2L, updateRequest);
        });

        verify(userRepository).findById(2L);
        verify(userRepository, never()).save(any());
    }
}
