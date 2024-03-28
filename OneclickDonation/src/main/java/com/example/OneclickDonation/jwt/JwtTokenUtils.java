package com.example.OneclickDonation.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;

@Slf4j
@Component
public class JwtTokenUtils {
    private final Key singningKey;
    private final JwtParser jwtParser;

    public JwtTokenUtils(
               @Value("${jwt.secret}")
               String jwtSecret
       ) {
           this.singningKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
           this.jwtParser = Jwts.parserBuilder().setSigningKey(this.singningKey).build();
        }

    public String generateToken(UserDetails userDetails) {
        Instant now = Instant.now();
        Claims jwtClaims = Jwts.claims()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(60 * 60 * 24 * 7 * 2)));

        log.info("토큰 정보: {}", jwtClaims);
        return Jwts.builder()
                .setClaims(jwtClaims)
                .signWith(this.singningKey)
                .compact();
    }

    public boolean validate(String token) {
        try {
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.warn("유효하지 않은 JWT입니다: {}", e.toString());
        }
        return false;
    }

    public Claims parseClaims(String token) {
        return jwtParser
                .parseClaimsJws(token)
                .getBody();
    }
}


