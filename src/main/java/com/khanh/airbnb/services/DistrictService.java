package com.khanh.airbnb.services;

import com.khanh.airbnb.dto.location.DistrictResponseDto;

import java.util.List;

public interface DistrictService {
    List<DistrictResponseDto> findByCityId(Integer cityId);
}
