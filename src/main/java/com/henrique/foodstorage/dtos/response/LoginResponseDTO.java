package com.henrique.foodstorage.dtos.response;

public record LoginResponseDTO(
        UserResponseDTO  user,
        TokensDTO tokens
) {
}
