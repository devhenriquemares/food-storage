package com.henrique.foodstorage.dtos.response;

public record TokensDTO(
        String accessToken,
        String refreshToken
) {
}
