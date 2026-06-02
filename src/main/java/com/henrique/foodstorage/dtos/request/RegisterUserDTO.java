package com.henrique.foodstorage.dtos.request;

import jakarta.validation.constraints.*;

public record RegisterUserDTO(
        @Size(max = 100)
        @NotBlank
        String username,

        @Size(min = 8)
        @NotBlank
        String password,

        @Email
        @NotBlank
        String email
) {
}
