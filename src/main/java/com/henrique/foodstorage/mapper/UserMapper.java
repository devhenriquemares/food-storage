package com.henrique.foodstorage.mapper;

import com.henrique.foodstorage.dtos.response.UserResponseDTO;
import com.henrique.foodstorage.entity.UserAccount;

public class UserMapper {
    public static UserResponseDTO mapToDTO(UserAccount user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
