package com.henrique.foodstorage.dtos.response;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String username,
        String email
) {
}
