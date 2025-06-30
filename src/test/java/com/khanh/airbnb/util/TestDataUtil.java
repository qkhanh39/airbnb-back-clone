package com.khanh.airbnb.util;

import com.khanh.airbnb.domain.entities.UserEntity;
import com.khanh.airbnb.dto.registration.RegistrationRequestDto;

public class TestDataUtil {

    public static UserEntity createTestUser1() {
        return UserEntity.builder()
                .username("user1")
                .email("user1@example.com")
                .password("password1")
                .fullName("Test User One")
                .build();
    }

    public static RegistrationRequestDto createTestUserDto1(){
        return RegistrationRequestDto.builder()
                .username("user1")
                .email("user1@example.com")
                .password("password1")
                .fullName("Test User One")
                .build();
    }

    public static UserEntity createTestUser2() {
        return UserEntity.builder()
                .username("user2")
                .email("user2@example.com")
                .password("password2")
                .fullName("Test User Two")
                .build();
    }

    public static UserEntity createTestUser3() {
        return UserEntity.builder()
                .username("user3")
                .email("user3@example.com")
                .password("password3")
                .fullName("Test User Three")
                .build();
    }
}
