package com.henrique.foodstorage.dtos.response;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErrorResponse(
        Integer statusCode,
        String error,
        String message,
        List<BindExceptionFieldErrorResponse> errors
) {
}
