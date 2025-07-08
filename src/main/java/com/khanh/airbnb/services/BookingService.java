package com.khanh.airbnb.services;

import com.khanh.airbnb.domain.entities.UserEntity;
import com.khanh.airbnb.dto.booking.BookingRequestDto;
import com.khanh.airbnb.dto.booking.BookingResponseDto;

import java.util.List;

public interface BookingService {
    BookingResponseDto bookHomestay(UserEntity user, BookingRequestDto request);

    BookingResponseDto confirmBooking(UserEntity user, Long bookingId);

    List<BookingResponseDto> getBookings(UserEntity user);

    BookingResponseDto getBooking(UserEntity user, Long bookingId);
}
