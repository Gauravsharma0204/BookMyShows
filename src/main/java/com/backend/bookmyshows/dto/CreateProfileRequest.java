package com.backend.bookmyshows.dto;

import jakarta.validation.constraints.*;

public record CreateProfileRequest(
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Email @Size(max = 200) String email,
        @NotNull @Pattern(regexp = "^[6-9]\\d{9}$") String phone)
{

}
