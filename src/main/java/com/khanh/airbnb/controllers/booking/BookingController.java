package com.khanh.airbnb.controllers.booking;

import com.khanh.airbnb.domain.entities.UserEntity;
import com.khanh.airbnb.dto.booking.BookingRequestDto;
import com.khanh.airbnb.dto.booking.BookingResponseDto;
import com.khanh.airbnb.services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping(path = "/bookings")
    public ResponseEntity<BookingResponseDto> bookHomestay(@AuthenticationPrincipal UserEntity user,
                                                           @RequestBody BookingRequestDto request){
        BookingResponseDto response = bookingService.bookHomestay(user, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/host/bookings/{bookingId}/confirm")
    public ResponseEntity<BookingResponseDto> bookingConfirm (@AuthenticationPrincipal UserEntity user,
                                                              @PathVariable("bookingId") Long bookingId) {
        BookingResponseDto response = bookingService.confirmBooking(user, bookingId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/me/bookings")
    public ResponseEntity<List<BookingResponseDto>> getBookings (@AuthenticationPrincipal UserEntity user) {
        List<BookingResponseDto> response = bookingService.getBookings(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/me/bookings/{bookingId}")
    public ResponseEntity<BookingResponseDto> getBooking (@AuthenticationPrincipal UserEntity user,
                                                          @PathVariable Long bookingId) {
        BookingResponseDto response = bookingService.getBooking(user, bookingId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
