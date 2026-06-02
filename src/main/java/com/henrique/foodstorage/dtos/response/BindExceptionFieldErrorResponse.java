package com.henrique.foodstorage.dtos.response;

public record BindExceptionFieldErrorResponse(
        String field,
        String message,
        Object rejectedValue
) {
}
