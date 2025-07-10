package com.khanh.airbnb.services.impl;

import com.khanh.airbnb.domain.entities.CityEntity;
import com.khanh.airbnb.dto.location.CityResponseDto;
import com.khanh.airbnb.mappers.CityMapper;
import com.khanh.airbnb.repositories.CityRepository;
import com.khanh.airbnb.services.CityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public CityServiceImpl(CityRepository cityRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    @Override
    public List<CityResponseDto> getAllCities() {
        List<CityEntity> cityEntities = cityRepository.findAll();
        return cityEntities.stream().map(cityMapper::toDto).collect(Collectors.toList());
    }
}
