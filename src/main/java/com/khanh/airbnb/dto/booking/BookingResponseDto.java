package com.khanh.airbnb.dto.booking;

import com.khanh.airbnb.domain.enums.BookingStatus;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponseDto {
    private Long bookingId;
    private Long homestayId;
    private String homestayName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer guests;
    private BookingStatus status;
    private String currency;
    private Integer subtotal;
    private Integer discount;
    private Integer totalAmount;
    private String note;
    private LocalDateTime createdAt;
}
