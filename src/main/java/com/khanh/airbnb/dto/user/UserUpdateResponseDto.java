package com.khanh.airbnb.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateResponseDto {
    private String username;
    private String password;
    private String email;
    private String fullName;
}
