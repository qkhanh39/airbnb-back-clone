package com.khanh.airbnb.dto.registration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationResponseDto {
    private Long userId;
    private String username;
    private String email;
    private String fullName;
}
