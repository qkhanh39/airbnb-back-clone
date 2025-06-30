package com.khanh.airbnb.dto.registration;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationRequestDto {
    @NotNull
    @NotBlank(message = "username must not be empty")
    @Size(min = 2, max = 30, message = "username must be between 2 and 30 characters")
    private String username;


    @NotBlank(message = "password must not be empty")
    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Password must be minimum of eight characters, at least one uppercase letter, one lowercase letter and one number")
    @Size(min = 8, max = 16, message = "password must be between 8 and 16 characters")
    private String password;

    @Email(message = "email is not valid")
    @NotNull
    @NotBlank(message = "email must not be empty")
    private String email;

    @NotNull
    @NotBlank(message = "your full name must not be empty")
    @Size(max = 50, message = "your full name must not be longer than 50 characters")
    private String fullName;
}
