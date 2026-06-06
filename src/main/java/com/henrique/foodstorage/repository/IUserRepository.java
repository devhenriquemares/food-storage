package com.henrique.foodstorage.repository;

import com.henrique.foodstorage.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<UserAccount, UUID> {
    boolean existsByEmail(String email);

    @Query("""
        SELECT u FROM UserAccount u
        LEFT JOIN FETCH u.roles r
        WHERE u.email = :email
    """)
    Optional<UserAccount> findByEmail(String email);
}
