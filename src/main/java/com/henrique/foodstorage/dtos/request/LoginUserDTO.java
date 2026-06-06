package com.henrique.foodstorage.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginUserDTO(
        @Email
        @NotBlank
        String email,

        @Size(min = 8, max = 255)
        @NotBlank
        String password
) {
}
