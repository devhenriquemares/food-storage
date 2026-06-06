package com.henrique.foodstorage.exception;

import com.henrique.foodstorage.dtos.response.BindExceptionFieldErrorResponse;
import com.henrique.foodstorage.dtos.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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

    private ResponseEntity<ErrorResponse> handleError(HttpStatus status, String message, List<BindExceptionFieldErrorResponse> errors, Exception ex) {
        log.error("{} - {}", ex.getClass(), ex.getMessage(), ex);

        ErrorResponse body = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                message,
                errors
        );

        return ResponseEntity.status(status).body(body);
    }

    private ResponseEntity<ErrorResponse> handleError(HttpStatus status, List<BindExceptionFieldErrorResponse> errors, Exception ex) {
        return this.handleError(status, "", errors, ex);
    }

    private ResponseEntity<ErrorResponse> handleError(HttpStatus status, String message, Exception ex) {
        return this.handleError(status, message, List.of(), ex);
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

        return this.handleError(HttpStatus.BAD_REQUEST, errors, ex);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        return this.handleError(HttpStatus.BAD_REQUEST, "Email or password invalid", ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception ex) {
        return this.handleError(HttpStatus.INTERNAL_SERVER_ERROR, "Some internal server error occurred", ex);
    }
}
