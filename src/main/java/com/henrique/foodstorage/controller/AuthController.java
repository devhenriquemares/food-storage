package com.henrique.foodstorage.controller;

import com.henrique.foodstorage.dtos.request.RegisterUserDTO;
import com.henrique.foodstorage.dtos.response.LoginResponseDTO;
import com.henrique.foodstorage.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResponseDTO register(@Valid @RequestBody RegisterUserDTO request) {
        return authService.register(request);
    }
}
