package com.outsourcing.common.utils;


import com.outsourcing.common.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {
    //기본 셋팅
    public static final String BEARER_PREFIX = "Bearer ";

    private static final long TOKEN_TIME = 60 * 60 * 1000L; // 60분

    @Value("${jwt.secret.key}")
    private String secretKeyString;

    private SecretKey key;

    private JwtParser parser;


    @PostConstruct
    public void init() {

        byte[] bytes = Decoders.BASE64.decode(secretKeyString);

        this.key = Keys.hmacShaKeyFor(bytes);
        this.parser = Jwts.parser()
                .verifyWith(this.key)
                .build();
    }

    // 토큰 생성
    public String generateToken(String username, UserRole role, Long userId) {

        Date now = new Date();

        return Jwts.builder()
                .subject(userId.toString())
                .claim("role", role.name())
                .claim("username", username)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + TOKEN_TIME))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    // 토큰 검증
    public boolean validateToken(String token) {

        if (token == null || token.isBlank()) return false;

        try {

            parser.parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {

            log.debug("Invalid JWT: {}", e.toString());
            return false;
        }
    }


    // 토큰 복호화
    private Claims extractAllClaims(String token) {

        return parser.parseSignedClaims(token).getPayload();
    }

    public String extractUsername(String token) {

        return extractAllClaims(token).get("username",String.class);
    }

    public String extractRole(String token) {

        return extractAllClaims(token).get("role", String.class);
    }

    public Long extractUserId(String token) {

        return Long.parseLong(extractAllClaims(token).getSubject());
    }

}


