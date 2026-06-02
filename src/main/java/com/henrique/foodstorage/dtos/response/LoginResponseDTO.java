package com.henrique.foodstorage.dtos.response;

import com.henrique.foodstorage.dtos.TokensDTO;
import com.henrique.foodstorage.entity.UserAccount;

public record LoginResponseDTO(
        UserAccount user,
        TokensDTO tokens
) {
}
