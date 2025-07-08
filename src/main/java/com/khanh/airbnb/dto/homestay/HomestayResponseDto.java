package com.khanh.airbnb.dto.homestay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomestayResponseDto {
    private Long homestayId;
    private String fullName;
    private String name;
    private String description;
    private String address;
    private String wardName;
    private String cityName;
    private String districtName;
    private Integer maxGuests;
    private Integer priceDefault;
    private LocalDateTime createdAt;
}
