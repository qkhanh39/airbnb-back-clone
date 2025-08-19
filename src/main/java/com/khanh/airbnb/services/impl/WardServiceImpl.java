package com.khanh.airbnb.services.impl;

import com.khanh.airbnb.domain.entities.DistrictEntity;
import com.khanh.airbnb.domain.entities.WardEntity;
import com.khanh.airbnb.dto.location.WardResponseDto;
import com.khanh.airbnb.exceptions.ResourceNotFoundException;
import com.khanh.airbnb.mappers.WardMapper;
import com.khanh.airbnb.repositories.DistrictRepository;
import com.khanh.airbnb.repositories.WardRepository;
import com.khanh.airbnb.services.WardService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WardServiceImpl implements WardService {
    private DistrictRepository districtRepository;
    private WardRepository wardRepository;
    private WardMapper wardMapper;

    public WardServiceImpl(DistrictRepository districtRepository,
                           WardRepository wardRepository,
                           WardMapper wardMapper) {
        this.wardRepository = wardRepository;
        this.wardMapper = wardMapper;
        this.districtRepository = districtRepository;
    }

    @Override
    public List<WardResponseDto> findByDistrictId(Integer districtId) {
        DistrictEntity district = districtRepository.findById(districtId)
                .orElseThrow(() -> new ResourceNotFoundException("District not found"));
        List<WardEntity> wards = wardRepository.findByDistrictEntity(district);
        return wards.stream().map(wardMapper::toDto).collect(Collectors.toList());
    }
}
