package com.khanh.airbnb.controllers.user;

import com.khanh.airbnb.domain.entities.UserEntity;
import com.khanh.airbnb.dto.user.UserResponseDto;
import com.khanh.airbnb.dto.user.UserUpdateRequestDto;
import com.khanh.airbnb.dto.user.UserUpdateResponseDto;
import com.khanh.airbnb.mappers.UserMapper;
import com.khanh.airbnb.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping(path = "/me")
    public ResponseEntity<UserResponseDto> getUserInformation(@AuthenticationPrincipal UserEntity userEntity) {
        UserResponseDto userResponse = userMapper.toUserResponseDto(userEntity);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PatchMapping(path = "/me")
    public ResponseEntity<UserUpdateResponseDto> updateUser(@AuthenticationPrincipal UserEntity authenticatedUser,
                                                            @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        UserEntity userEntity = userMapper.toUserEntity(userUpdateRequestDto);
        UserEntity userUpdated = userService.partialUpdate(authenticatedUser.getUserId(), userEntity);
        UserUpdateResponseDto userUpdatedDto = userMapper.toUserUpdateResponseDto(userUpdated);
        return new ResponseEntity<>(userUpdatedDto, HttpStatus.OK);
    }
}
