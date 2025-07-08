package com.khanh.airbnb.mappers;

import com.khanh.airbnb.domain.entities.CityEntity;
import com.khanh.airbnb.dto.location.CityResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityResponseDto toDto(CityEntity cityEntity);
}
