package com.khanh.airbnb.services;

import com.khanh.airbnb.domain.entities.HomestayEntity;

import java.time.LocalDate;

public interface HomestayAvailabilityService {
    boolean isHomestayBooked(HomestayEntity homestay, LocalDate checkInDate, LocalDate checkOutDate);
}
