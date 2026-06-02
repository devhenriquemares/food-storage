package com.henrique.foodstorage.service;

import com.henrique.foodstorage.dtos.TokensDTO;
import com.henrique.foodstorage.entity.UserAccount;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Component
public class TokenService {
    private final SecretKey secret;

    public TokenService(@Value("${jwt.secret}") String secret) {
        this.secret = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public TokensDTO generateTokens(UserAccount user) {
        Instant expiration = Instant.now().plus(1, ChronoUnit.HOURS);
        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        String token = Jwts.builder()
                .subject(user.getUsername())
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(Date.from(expiration))
                .signWith(secret)
                .compact();

        return new TokensDTO(token);
    }
}
