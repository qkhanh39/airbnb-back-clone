package com.khanh.airbnb.dto.homestay;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomestayRequestDto {
    private String name;
    private String description;
    private String address;
    private Integer wardId;
    private Integer districtId;
    private Integer cityId;
    private Integer maxGuests;
    private Integer priceDefault;
    private LocalDate availableFrom;
    private LocalDate availableTo;
}
