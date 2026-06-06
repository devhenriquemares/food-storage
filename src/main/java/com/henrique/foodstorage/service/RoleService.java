package com.henrique.foodstorage.service;

import com.henrique.foodstorage.entity.Role;
import com.henrique.foodstorage.enums.Roles;
import com.henrique.foodstorage.repository.IRoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private final IRoleRepository roleRepository;

    public RoleService(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(Roles role) {
        return roleRepository.findByName(role)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
    }

    public Set<Role> mapEnumToEntitySet(Set<Roles> roles) {
        return roles.stream()
                .map(this::findByName)
                .collect(Collectors.toSet());
    }
}
