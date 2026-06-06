package com.henrique.foodstorage.dtos.request;

import com.henrique.foodstorage.enums.Roles;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Set;

public record CreateUserDTO(
        @Size(min = 3, max = 100)
        @NotBlank
        String username,

        @Size(min = 8, max = 255)
        @NotBlank
        String password,

        @Email
        @NotBlank
        String email,

        @NotEmpty
        Set<@NotNull Roles> roles
) {
}
