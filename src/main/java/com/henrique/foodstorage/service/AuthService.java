package com.henrique.foodstorage.service;

import com.henrique.foodstorage.dtos.request.CreateUserDTO;
import com.henrique.foodstorage.dtos.request.LoginUserDTO;
import com.henrique.foodstorage.dtos.response.TokensDTO;
import com.henrique.foodstorage.dtos.request.RegisterUserDTO;
import com.henrique.foodstorage.dtos.response.LoginResponseDTO;
import com.henrique.foodstorage.dtos.response.UserResponseDTO;
import com.henrique.foodstorage.entity.Role;
import com.henrique.foodstorage.entity.UserAccount;
import com.henrique.foodstorage.enums.Roles;
import com.henrique.foodstorage.mapper.UserMapper;
import com.henrique.foodstorage.repository.IRoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AuthService {
    private final TokenService tokenService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthService(TokenService tokenService, UserService userService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    public LoginResponseDTO register(RegisterUserDTO registerDTO) {
        UserResponseDTO user = userService.save(new CreateUserDTO(
                registerDTO.username(),
                registerDTO.password(),
                registerDTO.email(),
                Set.of(Roles.USER)
        ));
        TokensDTO tokens = tokenService.generateTokens(user.email());

        return new LoginResponseDTO(user, tokens);
    }

    public LoginResponseDTO login(LoginUserDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.email(),
                loginDTO.password()
        ));
        UserAccount user = (UserAccount) authentication.getPrincipal();
        TokensDTO tokens = tokenService.generateTokens(user.getEmail());

        return new LoginResponseDTO(UserMapper.mapToDTO(user), tokens);
    }
}
