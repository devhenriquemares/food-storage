package com.henrique.foodstorage.service;

import com.henrique.foodstorage.dtos.TokenPayloadDTO;
import com.henrique.foodstorage.dtos.response.TokensDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class TokenService {
    private final SecretKey accessSecret;
    private final SecretKey refreshSecret;
    private final String roleClaimKey = "roles";
    private final CustomUserDetailsService userDetailsService;

    public TokenService(
            @Value("${jwt.access.secret}") String accessSecret,
            @Value("${jwt.refresh.secret}") String refreshSecret,
            CustomUserDetailsService userDetailsService
    ) {
        this.accessSecret = Keys.hmacShaKeyFor(accessSecret.getBytes(StandardCharsets.UTF_8));
        this.refreshSecret = Keys.hmacShaKeyFor(refreshSecret.getBytes(StandardCharsets.UTF_8));
        this.userDetailsService = userDetailsService;
    }

    public TokensDTO generateTokens(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        List<String> authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        String accessToken = this.generateAccessToken(email, authorities);
        String refreshToken = this.generateRefreshToken(email, authorities);

        return new TokensDTO(accessToken, refreshToken);
    }

    private String generateAccessToken(String sub, List<String> authorities) {
        Date expiration = Date.from(
                Instant.now().plus(1, ChronoUnit.HOURS)
        );

        return Jwts.builder()
                .subject(sub)
                .claim(this.roleClaimKey, authorities)
                .issuedAt(new Date())
                .expiration(expiration)
                .signWith(this.accessSecret)
                .compact();
    }

    private String generateRefreshToken(String sub, List<String> authorities) {
        Date expiration = Date.from(
                Instant.now().plus(7, ChronoUnit.DAYS)
        );

        return Jwts.builder()
                .subject(sub)
                .claim(this.roleClaimKey, authorities)
                .issuedAt(new Date())
                .expiration(expiration)
                .signWith(this.refreshSecret)
                .compact();
    }

    public TokenPayloadDTO extractAccessTokenPayload(String token) {
        return this.extractTokenPayload(token, accessSecret);
    }

    public TokenPayloadDTO extractRefreshTokenPayload(String token) {
        return this.extractTokenPayload(token, refreshSecret);
    }

    private TokenPayloadDTO extractTokenPayload(String token, SecretKey secret) {
        Claims payload = this.validateToken(token, secret);
        List<String> roles = this.extractRolesFromPayload(payload);

        return this.mapPayloadToDTO(payload, roles);
    }

    private List<String> extractRolesFromPayload(Claims payload) {
        return ((List<?>) payload.get(this.roleClaimKey, List.class))
                .stream()
                .map(String.class::cast)
                .toList();
    }

    private TokenPayloadDTO mapPayloadToDTO(Claims payload, List<String> roles) {
        return new TokenPayloadDTO(
                payload.getSubject(),
                roles,
                payload.getIssuedAt(),
                payload.getExpiration()
        );
    }

    private Claims validateToken(String token, SecretKey secret) {
        return Jwts.parser()
                .verifyWith(secret)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
