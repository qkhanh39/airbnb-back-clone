package com.khanh.airbnb.mappers;

import com.khanh.airbnb.domain.entities.DistrictEntity;
import com.khanh.airbnb.dto.location.DistrictResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DistrictMapper {
    DistrictResponseDto toDto(DistrictEntity districtEntity);
}
