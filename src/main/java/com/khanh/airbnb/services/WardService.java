package com.khanh.airbnb.services;

import com.khanh.airbnb.dto.location.WardResponseDto;

import java.util.List;

public interface WardService {
    List<WardResponseDto> findByDistrictId(Integer districtId);
}
