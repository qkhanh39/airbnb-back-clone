package com.khanh.airbnb.services.impl;

import com.khanh.airbnb.domain.entities.BookingEntity;
import com.khanh.airbnb.domain.entities.HomestayAvailabilityEntity;
import com.khanh.airbnb.domain.entities.HomestayEntity;
import com.khanh.airbnb.domain.entities.UserEntity;
import com.khanh.airbnb.domain.enums.BookingStatus;
import com.khanh.airbnb.domain.enums.HomestayStatus;
import com.khanh.airbnb.domain.keys.HomestayAvailabilityKey;
import com.khanh.airbnb.dto.booking.BookingRequestDto;
import com.khanh.airbnb.dto.booking.BookingResponseDto;
import com.khanh.airbnb.exceptions.AccessDeniedException;
import com.khanh.airbnb.exceptions.ResourceNotFoundException;
import com.khanh.airbnb.mappers.BookingMapper;
import com.khanh.airbnb.repositories.BookingRepository;
import com.khanh.airbnb.repositories.HomestayAvailabilityRepository;
import com.khanh.airbnb.repositories.HomestayRepository;
import com.khanh.airbnb.services.BookingService;
import com.khanh.airbnb.services.HomestayAvailabilityService;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final HomestayRepository homestayRepository;
    private final BookingRepository bookingRepository;
    private final HomestayAvailabilityService homestayAvailabilityService;
    private final BookingMapper bookingMapper;
    private final HomestayAvailabilityRepository homestayAvailabilityRepository;

    public BookingServiceImpl (HomestayRepository homestayRepository,
                               BookingRepository bookingRepository,
                               HomestayAvailabilityService homestayAvailabilityService,
                               BookingMapper bookingMapper,
                               HomestayAvailabilityRepository homestayAvailabilityRepository) {
        this.homestayRepository = homestayRepository;
        this.bookingRepository = bookingRepository;
        this.homestayAvailabilityService = homestayAvailabilityService;
        this.bookingMapper = bookingMapper;
        this.homestayAvailabilityRepository = homestayAvailabilityRepository;
    }

    @Override
    public BookingResponseDto bookHomestay(UserEntity user, BookingRequestDto request) {
        HomestayEntity homestay = homestayRepository.findById(request.getHomestayId())
                .orElseThrow(() -> new RuntimeException("Homestay not found"));
        if (homestay.getMaxGuests() < request.getGuests()){
            throw new IllegalArgumentException("This homestay does not allow more than " + homestay.getMaxGuests());
        }

        if (request.getCheckOutDate().isBefore(request.getCheckInDate())) {
            throw new IllegalArgumentException("Check out date must be after check in day");
        }

        if (homestayAvailabilityService.isHomestayBooked(homestay, request.getCheckInDate(), request.getCheckOutDate())) {
            throw new IllegalArgumentException("Homestay is booked");
        }

        BookingEntity booking = bookingMapper.toEntity(request);
        booking.setUserEntity(user);
        booking.setCurrency("USD");
        booking.setHomestayEntity(homestay);
        booking.setStatus(BookingStatus.PENDING);

        BookingPrice bookingPrice = calculateTotalPrice(homestay, request.getCheckInDate(), request.getCheckOutDate());

        booking.setDiscount(bookingPrice.getDiscount());
        booking.setSubtotal(bookingPrice.getSubtotal());
        booking.setTotalAmount(bookingPrice.getTotalAmount());

        bookingRepository.save(booking);

        for (LocalDate date = request.getCheckInDate(); date.isBefore(request.getCheckOutDate()); date = date.plusDays(1)) {
            HomestayAvailabilityKey key = new HomestayAvailabilityKey(homestay.getHomestayId(), date);
            LocalDate currentDate = date;
            HomestayAvailabilityEntity availability = homestayAvailabilityRepository.findById(key)
                    .orElseThrow(() -> new IllegalArgumentException("Availability not found for date: " + currentDate));

            availability.setStatus(HomestayStatus.BOOKED);
            availability.setBookingEntity(booking);
            homestayAvailabilityRepository.save(availability);

        }

        return bookingMapper.toDto(booking);
    }

    @Getter
    public static class BookingPrice {
        private final int subtotal;
        private final int discount;
        private final int totalAmount;

        public BookingPrice (int subtotal, int discount, int totalAmount) {
            this.subtotal = subtotal;
            this.discount = discount;
            this.totalAmount = totalAmount;
        }
    }

    private BookingPrice calculateTotalPrice(HomestayEntity homestay, LocalDate checkInDate, LocalDate checkOutDate) {
        int nights = (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        int subtotal = 0;
        for (LocalDate date = checkInDate; date.isBefore(checkOutDate); date = date.plusDays(1)) {
            Optional<HomestayAvailabilityEntity> opAvailability = homestayAvailabilityRepository.findById(new HomestayAvailabilityKey(homestay.getHomestayId(), date));
            int dailyPrice = opAvailability
                    .map(HomestayAvailabilityEntity::getPrice)
                    .orElse(homestay.getPriceDefault());
            subtotal += dailyPrice;
        }

        int discountPercent = 0;

        if (nights >= 7) {
            discountPercent = 5;
        }

        int discount = subtotal * discountPercent / 100;
        int total = subtotal - discount;

        return new BookingPrice(subtotal, discount, total);
    }

    @Override
    @Transactional
    public BookingResponseDto confirmBooking(UserEntity user, Long bookingId) {
        Optional<BookingEntity> opBooking = bookingRepository.findById(bookingId);
        if (opBooking.isEmpty()) {
            throw new ResourceNotFoundException("Booking is not exist");
        }
        BookingEntity booking = opBooking.get();
        HomestayEntity homestay = booking.getHomestayEntity();
        if (!user.getUserId().equals(homestay.getUserEntity().getUserId())) {
            throw new AccessDeniedException("You are not the host of this homestay");
        }

        if (!booking.getStatus().equals(BookingStatus.PENDING)) {
            throw new IllegalStateException("Only PENDING bookings can be confirmed");
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);
        return bookingMapper.toDto(booking);
    }

    @Override
    public List<BookingResponseDto> getBookings(UserEntity user) {
        List<BookingEntity> bookings = bookingRepository.findByUserEntity(user);
        return bookings.stream().map(bookingMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public BookingResponseDto getBooking(UserEntity user, Long bookingId) {
        BookingEntity booking = bookingRepository.findByUserEntityAndBookingId(user, bookingId);
        return bookingMapper.toDto(booking);
    }
}
