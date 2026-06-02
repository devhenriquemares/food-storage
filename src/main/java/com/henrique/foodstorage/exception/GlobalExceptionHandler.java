package com.henrique.foodstorage.exception;

import com.henrique.foodstorage.dtos.response.BindExceptionFieldErrorResponse;
import com.henrique.foodstorage.dtos.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ResponseEntity<ErrorResponse> handleError(HttpStatus status, List<BindExceptionFieldErrorResponse> errors) {
        ErrorResponse body = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                null,
                errors
        );

        return ResponseEntity.status(status).body(body);
    }

    private ResponseEntity<ErrorResponse> handleError(HttpStatus status, String message) {
        ErrorResponse body = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                message,
                List.of()
        );

        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(BindException ex) {
        List<BindExceptionFieldErrorResponse> errors = ex.getFieldErrors()
                .stream()
                .map(fieldError -> new BindExceptionFieldErrorResponse(
                        fieldError.getField(),
                        fieldError.getDefaultMessage(),
                        fieldError.getRejectedValue()
                ))
                .toList();

        return this.handleError(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception ex) {
        return this.handleError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}
