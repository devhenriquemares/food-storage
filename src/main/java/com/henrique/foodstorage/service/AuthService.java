package com.henrique.foodstorage.service;

import com.henrique.foodstorage.dtos.TokensDTO;
import com.henrique.foodstorage.dtos.request.RegisterUserDTO;
import com.henrique.foodstorage.dtos.response.LoginResponseDTO;
import com.henrique.foodstorage.entity.Role;
import com.henrique.foodstorage.entity.UserAccount;
import com.henrique.foodstorage.enums.Roles;
import com.henrique.foodstorage.repository.IUserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final IUserRepository userRepository;
    private final TokenService tokenService;

    public AuthService(PasswordEncoder passwordEncoder, IUserRepository userRepository, TokenService tokenService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public LoginResponseDTO register(RegisterUserDTO registerDTO) {
        String email = registerDTO.email();
        if (userRepository.existsByEmail(email)) throw new BadCredentialsException("Email already registered");

        String username = registerDTO.username();
        String password = passwordEncoder.encode(registerDTO.password());

        UserAccount user = new UserAccount();
        user.setName(username);
        user.setEmail(email);
        user.setPassword(password);

        userRepository.save(user);
        TokensDTO tokens = tokenService.generateTokens(user);

        return new LoginResponseDTO(user, tokens);
    }
}
