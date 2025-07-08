package com.khanh.airbnb.services.impl;

import com.khanh.airbnb.domain.entities.HomestayEntity;
import com.khanh.airbnb.domain.enums.HomestayStatus;
import com.khanh.airbnb.repositories.HomestayAvailabilityRepository;
import com.khanh.airbnb.services.HomestayAvailabilityService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HomestayAvailabilityServiceImpl implements HomestayAvailabilityService
{
    private final HomestayAvailabilityRepository homestayAvailabilityRepository;

    public HomestayAvailabilityServiceImpl(HomestayAvailabilityRepository homestayAvailabilityRepository) {
        this.homestayAvailabilityRepository = homestayAvailabilityRepository;
    }

    @Override
    public boolean isHomestayBooked(HomestayEntity homestay, LocalDate checkInDate, LocalDate checkOutDate) {
        List<HomestayStatus> invalidStatuses = List.of(HomestayStatus.BOOKED);

        return homestayAvailabilityRepository.existsByHomestayEntityAndIdDateBetweenAndStatusIn(
                homestay,
                checkInDate,
                checkOutDate,
                invalidStatuses
        );
    }
}
