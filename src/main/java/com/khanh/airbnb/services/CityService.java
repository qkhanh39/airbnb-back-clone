package com.khanh.airbnb.services;

import com.khanh.airbnb.dto.location.CityResponseDto;

import java.util.List;

public interface CityService {
    List<CityResponseDto> getAllCities();
}
