package com.khanh.airbnb.mappers;

import com.khanh.airbnb.domain.entities.WardEntity;
import com.khanh.airbnb.dto.location.WardResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WardMapper {
    WardResponseDto toDto(WardEntity wardEntity);
}
