package com.khanh.airbnb.dto.booking;

import com.khanh.airbnb.domain.entities.HomestayEntity;
import com.khanh.airbnb.domain.entities.UserEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingRequestDto {
    private Long homestayId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer guests;
    private String currency;
    private String note;
}
