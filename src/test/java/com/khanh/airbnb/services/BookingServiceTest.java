package com.khanh.airbnb.services;

import com.khanh.airbnb.domain.entities.BookingEntity;
import com.khanh.airbnb.domain.entities.HomestayAvailabilityEntity;
import com.khanh.airbnb.domain.entities.HomestayEntity;
import com.khanh.airbnb.domain.entities.UserEntity;
import com.khanh.airbnb.domain.enums.BookingStatus;
import com.khanh.airbnb.domain.keys.HomestayAvailabilityKey;
import com.khanh.airbnb.dto.booking.BookingRequestDto;
import com.khanh.airbnb.dto.booking.BookingResponseDto;
import com.khanh.airbnb.exceptions.AccessDeniedException;
import com.khanh.airbnb.exceptions.ResourceNotFoundException;
import com.khanh.airbnb.mappers.BookingMapper;
import com.khanh.airbnb.repositories.BookingRepository;
import com.khanh.airbnb.repositories.HomestayAvailabilityRepository;
import com.khanh.airbnb.repositories.HomestayRepository;
import com.khanh.airbnb.services.impl.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
    @Mock
    private HomestayRepository homestayRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private HomestayAvailabilityService homestayAvailabilityService;

    @Mock
    private BookingMapper bookingMapper;

    @Mock
    private HomestayAvailabilityRepository homestayAvailabilityRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private UserEntity user;
    private HomestayEntity homestay;
    private BookingRequestDto request;
    private BookingEntity booking;
    private BookingResponseDto response;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setUserId(1L);

        homestay = new HomestayEntity();
        homestay.setHomestayId(1L);
        homestay.setMaxGuests(4);
        homestay.setPriceDefault(100);
        homestay.setUserEntity(user);

        request = new BookingRequestDto();
        request.setHomestayId(1L);
        request.setGuests(2);
        request.setCheckInDate(LocalDate.of(2025, 8, 5));
        request.setCheckOutDate(LocalDate.of(2025, 8, 7));

        booking = new BookingEntity();
        booking.setBookingId(1L);
        booking.setUserEntity(user);
        booking.setStatus(BookingStatus.PENDING);
        booking.setHomestayEntity(homestay);

        response = new BookingResponseDto();
        response.setBookingId(1L);
    }

    @Test
    public void testBookingHomestaySuccess() {
        when(homestayRepository.findById(1L)).thenReturn(Optional.of(homestay));
        when(homestayAvailabilityService.isHomestayBooked(any(), any(), any())).thenReturn(false);
        when(bookingMapper.toEntity(request)).thenReturn(booking);
        when(bookingMapper.toDto(booking)).thenReturn(response);

        for (LocalDate d = request.getCheckInDate(); d.isBefore(request.getCheckOutDate()); d = d.plusDays(1)) {
            HomestayAvailabilityKey key = new HomestayAvailabilityKey(homestay.getHomestayId(), d);
            HomestayAvailabilityEntity availability = new HomestayAvailabilityEntity();
            availability.setPrice(100);
            availability.setId(key);
            when(homestayAvailabilityRepository.findById(key)).thenReturn(Optional.of(availability));
        }

        BookingResponseDto result = bookingService.bookHomestay(user, request);
        assertNotNull(result);
        assertEquals(1L, result.getBookingId());
        verify(bookingRepository).save(any(BookingEntity.class));
    }

    @Test
    public void testHomestayNotFound() {
        when(homestayRepository.findById(1L)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> bookingService.bookHomestay(user, request));
        assertEquals("Homestay not found", exception.getMessage());
    }

    @Test
    public void testBookHomestayGuestsExceedLimit() {
        homestay.setMaxGuests(1);
        when(homestayRepository.findById(1L)).thenReturn(Optional.of(homestay));
        request.setGuests(3);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> bookingService.bookHomestay(user, request));
        assertTrue(ex.getMessage().contains("does not allow more than"));
    }

    @Test
    public void testBookHomestayInvalidDateRange() {
        when(homestayRepository.findById(1L)).thenReturn(Optional.of(homestay));
        request.setCheckOutDate(request.getCheckInDate().minusDays(1));
        assertThrows(IllegalArgumentException.class, () -> bookingService.bookHomestay(user, request));
    }

    @Test
    public void testBookHomestayAlreadyBooked() {
        when(homestayRepository.findById(1L)).thenReturn(Optional.of(homestay));
        when(homestayAvailabilityService.isHomestayBooked(any(), any(), any())).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> bookingService.bookHomestay(user, request));
    }

    @Test
    public void testConfirmBookingSuccess() {
        booking.setStatus(BookingStatus.PENDING);
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(bookingMapper.toDto(booking)).thenReturn(response);

        BookingResponseDto result = bookingService.confirmBooking(user,1L);
        assertEquals(BookingStatus.CONFIRMED, booking.getStatus());
        assertEquals(1L, result.getBookingId());
        verify(bookingRepository).save(booking);
    }

    @Test
    public void testConfirmBookingNotHost() {
        UserEntity notHost = new UserEntity();
        notHost.setUserId(2L);
        booking.setStatus(BookingStatus.PENDING);
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        assertThrows(AccessDeniedException.class, () -> bookingService.confirmBooking(notHost, 1L));
    }

    @Test
    public void testGetBookings() {
        when(bookingRepository.findByUserEntity(user)).thenReturn(List.of(booking));
        when(bookingMapper.toDto(booking)).thenReturn(response);

        List<BookingResponseDto> result = bookingService.getBookings(user);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getBookingId());
    }

    @Test
    public void testGetBooking() {
        when(bookingRepository.findByUserEntityAndBookingId(user, 1L)).thenReturn(booking);
        when(bookingMapper.toDto(booking)).thenReturn(response);

        BookingResponseDto result = bookingService.getBooking(user, 1L);
        assertEquals(1L, result.getBookingId());
    }
}
