package com.henrique.foodstorage.service;

import com.henrique.foodstorage.dtos.request.CreateUserDTO;
import com.henrique.foodstorage.dtos.request.RegisterUserDTO;
import com.henrique.foodstorage.dtos.response.UserResponseDTO;
import com.henrique.foodstorage.entity.Role;
import com.henrique.foodstorage.entity.UserAccount;
import com.henrique.foodstorage.enums.Roles;
import com.henrique.foodstorage.mapper.UserMapper;
import com.henrique.foodstorage.repository.IUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public UserResponseDTO save(CreateUserDTO dto) {
        String email = dto.email();
        if (userRepository.existsByEmail(email)) throw new BadCredentialsException("Email already registered");

        String username = dto.username();
        String password = passwordEncoder.encode(dto.password());
        Set<Role> roles = roleService.mapEnumToEntitySet(dto.roles());

        UserAccount user = new UserAccount();
        user.setName(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(roles);

        return UserMapper.mapToDTO(userRepository.save(user));
    }
}
