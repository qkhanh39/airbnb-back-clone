package com.khanh.airbnb.services.impl;

import com.khanh.airbnb.domain.entities.*;
import com.khanh.airbnb.domain.enums.HomestayStatus;
import com.khanh.airbnb.domain.keys.HomestayAvailabilityKey;
import com.khanh.airbnb.dto.homestay.HomestayRequestDto;
import com.khanh.airbnb.dto.homestay.HomestayResponseDto;
import com.khanh.airbnb.exceptions.AccessDeniedException;
import com.khanh.airbnb.exceptions.ResourceNotFoundException;
import com.khanh.airbnb.mappers.HomestayMapper;
import com.khanh.airbnb.repositories.*;
import com.khanh.airbnb.services.HomestayAvailabilityService;
import com.khanh.airbnb.services.HomestayService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class HomestayServiceImp implements HomestayService {

    private HomestayRepository homestayRepository;
    private CityRepository cityRepository;
    private WardRepository wardRepository;
    private DistrictRepository districtRepository;
    private HomestayMapper homestayMapper;
    private HomestayAvailabilityRepository homestayAvailabilityRepository;

    public HomestayServiceImp (HomestayRepository homestayRepository,
                               CityRepository cityRepository,
                               WardRepository wardRepository,
                               DistrictRepository districtRepository,
                               HomestayMapper homestayMapper,
                               HomestayAvailabilityRepository homestayAvailabilityRepository) {
        this.homestayRepository = homestayRepository;
        this.cityRepository = cityRepository;
        this.wardRepository = wardRepository;
        this.districtRepository = districtRepository;
        this.homestayMapper = homestayMapper;
        this.homestayAvailabilityRepository = homestayAvailabilityRepository;
    }

    @Override
    public HomestayResponseDto createHomestay(UserEntity user, HomestayRequestDto request) {
        CityEntity city = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new ResourceNotFoundException("City not found"));

        WardEntity ward = wardRepository.findById(request.getWardId())
                .orElseThrow(() -> new ResourceNotFoundException("Ward not found"));

        DistrictEntity district = districtRepository.findById(request.getDistrictId())
                .orElseThrow(() -> new ResourceNotFoundException("District not found"));


        HomestayEntity homestayEntity = HomestayEntity.builder()
                .name(request.getName())
                .address(request.getAddress())
                .cityEntity(city)
                .districtEntity(district)
                .wardEntity(ward)
                .userEntity(user)
                .priceDefault(request.getPriceDefault())
                .maxGuests(request.getMaxGuests())
                .description(request.getDescription())
                .build();
        homestayRepository.save(homestayEntity);
        List<HomestayAvailabilityEntity> availabilityList = new ArrayList<>();
        for (LocalDate date = request.getAvailableFrom(); date.isBefore(request.getAvailableTo()); date = date.plusDays(1)) {
            HomestayAvailabilityEntity availability = HomestayAvailabilityEntity.builder()
                    .id(new HomestayAvailabilityKey(homestayEntity.getHomestayId(), date))
                    .homestayEntity(homestayEntity)
                    .price(request.getPriceDefault())
                    .status(HomestayStatus.AVAILABLE)
                    .build();
            availabilityList.add(availability);
        }
        homestayAvailabilityRepository.saveAll(availabilityList);
        return homestayMapper.toDto(homestayEntity);
    }

    @Override
    public List<HomestayResponseDto> getHomestayByUser(UserEntity user) {
        List<HomestayEntity> homestays = homestayRepository.findByUserEntity(user);
        return homestays.stream().map(homestayMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<HomestayResponseDto> getHomestays() {
        List<HomestayEntity> homestays = homestayRepository.findAll();
        return homestays.stream().map(homestayMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public HomestayResponseDto getHomestay(Long homestayId) {
        HomestayEntity homestay = homestayRepository.findById(homestayId)
                .orElseThrow(() -> new ResourceNotFoundException("Homestay not found"));
        return homestayMapper.toDto(homestay);
    }

    @Override
    @Transactional
    public HomestayResponseDto updateHomestay(UserEntity user, Long homestayId, HomestayRequestDto request) {
        HomestayEntity homestay = homestayRepository.findById(homestayId)
                .orElseThrow(() -> new ResourceNotFoundException("Homestay not found"));

        if (!user.getUserId().equals(homestay.getUserEntity().getUserId())) {
            throw new AccessDeniedException("You are not the host of this homestay.");
        }

        if (request.getName() != null) homestay.setName(request.getName());
        if (request.getDescription() != null) homestay.setDescription(request.getDescription());
        if (request.getAddress() != null) homestay.setAddress(request.getAddress());
        if (request.getMaxGuests() != null) homestay.setMaxGuests(request.getMaxGuests());
        if (request.getPriceDefault() != null) homestay.setPriceDefault(request.getPriceDefault());

        if (request.getCityId() != null) {
            CityEntity city = cityRepository.findById(request.getCityId())
                    .orElseThrow(() -> new ResourceNotFoundException("City not found."));
            homestay.setCityEntity(city);
        }

        if (request.getDistrictId() != null) {
            DistrictEntity district = districtRepository.findById(request.getDistrictId())
                    .orElseThrow(() -> new ResourceNotFoundException("District not found."));
            homestay.setDistrictEntity(district);
        }

        if (request.getWardId() != null) {
            WardEntity ward = wardRepository.findById(request.getWardId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ward not found."));
            homestay.setWardEntity(ward);
        }

        LocalDate from = request.getAvailableFrom();
        LocalDate to = request.getAvailableTo();

        if (from != null && to != null) {
            if (to.isBefore(from)) {
                throw new IllegalArgumentException("AvailableTo must be after AvailableFrom.");
            }

            for (LocalDate date = from; date.isBefore(to); date = date.plusDays(1)) {
                HomestayAvailabilityKey key = new HomestayAvailabilityKey(homestayId, date);
                boolean exists = homestayAvailabilityRepository.existsById(key);

                if (!exists) {
                    HomestayAvailabilityEntity availability = HomestayAvailabilityEntity.builder()
                            .id(key)
                            .homestayEntity(homestay)
                            .price(homestay.getPriceDefault())
                            .status(HomestayStatus.AVAILABLE)
                            .build();
                    homestayAvailabilityRepository.save(availability);
                }
            }
        }
        homestayRepository.save(homestay);
        return homestayMapper.toDto(homestay);
    }
}
