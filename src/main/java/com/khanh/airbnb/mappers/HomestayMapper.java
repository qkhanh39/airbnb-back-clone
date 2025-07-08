package com.khanh.airbnb.mappers;

import com.khanh.airbnb.domain.entities.HomestayEntity;
import com.khanh.airbnb.dto.homestay.HomestayRequestDto;
import com.khanh.airbnb.dto.homestay.HomestayResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HomestayMapper {

    @Mapping(source = "userEntity.fullName", target = "fullName")
    @Mapping(source = "cityEntity.name", target = "cityName")
    @Mapping(source = "wardEntity.name", target = "wardName")
    @Mapping(source = "districtEntity.name", target = "districtName")
    HomestayResponseDto toDto(HomestayEntity homestayEntity);
}
