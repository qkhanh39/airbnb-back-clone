package com.khanh.airbnb.mappers;

import com.khanh.airbnb.domain.entities.UserEntity;
import com.khanh.airbnb.dto.registration.RegistrationRequestDto;
import com.khanh.airbnb.dto.registration.RegistrationResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {
    UserEntity toEntity(RegistrationRequestDto registrationRequestDto);
    RegistrationResponseDto toDto(UserEntity userEntity);
}
