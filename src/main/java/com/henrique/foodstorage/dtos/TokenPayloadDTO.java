package com.henrique.foodstorage.dtos;

import java.util.Date;
import java.util.List;

public record TokenPayloadDTO(
        String sub,
        List<String> roles,
        Date iat,
        Date exp
) {
}
