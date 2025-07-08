package com.khanh.airbnb.services;

import com.khanh.airbnb.domain.entities.UserEntity;
import com.khanh.airbnb.dto.homestay.HomestayRequestDto;
import com.khanh.airbnb.dto.homestay.HomestayResponseDto;

import java.util.List;

public interface HomestayService {
    HomestayResponseDto createHomestay(UserEntity user, HomestayRequestDto request);

    List<HomestayResponseDto> getHomestayByUser(UserEntity user);

    List<HomestayResponseDto> getHomestays();

    HomestayResponseDto getHomestay(Long homestayId);

    HomestayResponseDto updateHomestay(UserEntity user, Long homestayId, HomestayRequestDto request);
}
