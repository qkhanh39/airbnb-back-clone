package com.khanh.airbnb.services.impl;

import com.khanh.airbnb.domain.entities.CityEntity;
import com.khanh.airbnb.domain.entities.DistrictEntity;
import com.khanh.airbnb.dto.location.DistrictResponseDto;
import com.khanh.airbnb.mappers.DistrictMapper;
import com.khanh.airbnb.repositories.CityRepository;
import com.khanh.airbnb.repositories.DistrictRepository;
import com.khanh.airbnb.services.DistrictService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DistrictServiceImpl implements DistrictService {

    private CityRepository cityRepository;
    private DistrictRepository districtRepository;
    private DistrictMapper districtMapper;

    public DistrictServiceImpl (CityRepository cityRepository,
                                DistrictRepository districtRepository,
                                DistrictMapper districtMapper) {
        this.cityRepository = cityRepository;
        this.districtRepository = districtRepository;
        this.districtMapper = districtMapper;
    }

    @Override
    public List<DistrictResponseDto> findByCityId(Integer cityId) {
        Optional<CityEntity> city = cityRepository.findById(cityId);
        List<DistrictEntity> districts= districtRepository.findByCityEntity(city);
        return districts.stream().map(districtMapper::toDto).collect(Collectors.toList());
    }
}
