package com.khanh.airbnb.dto.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WardResponseDto {
    private Integer wardId;
    private String name;
}
