package com.khanh.airbnb.services;

import com.khanh.airbnb.domain.entities.HomestayEntity;
import com.khanh.airbnb.domain.enums.HomestayStatus;
import com.khanh.airbnb.repositories.HomestayAvailabilityRepository;
import com.khanh.airbnb.services.impl.HomestayAvailabilityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HomestayAvailabilityServiceTests {
    @Mock
    private HomestayAvailabilityRepository homestayAvailabilityRepository;

    @InjectMocks
    private HomestayAvailabilityServiceImpl homestayAvailabilityService;

    private HomestayEntity homestay;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    @BeforeEach
    void setUp() {
        homestay = new HomestayEntity();
        homestay.setHomestayId(1L);

        checkInDate = LocalDate.of(2025, 8, 10);
        checkOutDate = LocalDate.of(2025, 8, 15);
    }

    @Test
    public void testBookedHomestayReturnsTrue() {
        when(homestayAvailabilityRepository.existsByHomestayEntityAndIdDateBetweenAndStatusIn(
                eq(homestay),
                eq(checkInDate),
                eq(checkOutDate),
                eq(List.of(HomestayStatus.BOOKED))
        )).thenReturn(true);

        boolean result = homestayAvailabilityService.isHomestayBooked(homestay, checkInDate, checkOutDate);
        assertTrue(result);
    }

    @Test
    public void testNotBookedHomestayReturnFalse() {
        when(homestayAvailabilityRepository.existsByHomestayEntityAndIdDateBetweenAndStatusIn(
                eq(homestay),
                eq(checkInDate),
                eq(checkOutDate),
                eq(List.of(HomestayStatus.BOOKED))
        )).thenReturn(false);

        boolean result = homestayAvailabilityService.isHomestayBooked(homestay, checkInDate, checkOutDate);
        assertFalse(result);
    }
}
