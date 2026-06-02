package com.henrique.foodstorage.repository;

import com.henrique.foodstorage.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public interface IUserRepository extends JpaRepository<UserAccount, UUID> {
    boolean existsByEmail(String email);
}
