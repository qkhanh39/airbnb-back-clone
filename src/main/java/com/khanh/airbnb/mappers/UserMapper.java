package com.khanh.airbnb.mappers;

import com.khanh.airbnb.domain.entities.UserEntity;
import com.khanh.airbnb.dto.user.UserResponseDto;
import com.khanh.airbnb.dto.user.UserUpdateRequestDto;
import com.khanh.airbnb.dto.user.UserUpdateResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toUserResponseDto(UserEntity userEntity);

    UserEntity toUserEntity(UserUpdateRequestDto userUpdateRequestDto);

    UserUpdateResponseDto toUserUpdateResponseDto(UserEntity userUpdated);
}
