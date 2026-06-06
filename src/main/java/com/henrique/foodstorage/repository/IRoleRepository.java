package com.henrique.foodstorage.repository;

import com.henrique.foodstorage.entity.Role;
import com.henrique.foodstorage.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(Roles name);
}
