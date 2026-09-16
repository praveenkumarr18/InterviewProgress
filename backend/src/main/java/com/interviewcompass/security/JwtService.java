package com.interviewcompass.security;

import com.interviewcompass.entity.ApplicationUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private static final byte[] DEFAULT_SECRET_BYTES = new byte[] {
            0x69, 0x6e, 0x74, 0x65, 0x72, 0x76, 0x69, 0x65,
            0x77, 0x2d, 0x63, 0x6f, 0x6d, 0x70, 0x61, 0x73,
            0x73, 0x2d, 0x64, 0x65, 0x76, 0x65, 0x6c, 0x6f,
            0x70, 0x6d, 0x65, 0x6e, 0x74, 0x2d, 0x73, 0x65,
            0x63, 0x72, 0x65, 0x74, 0x2d, 0x6b, 0x65, 0x79,
            0x2d, 0x69, 0x6e, 0x74, 0x65, 0x72, 0x76, 0x69,
            0x65, 0x77, 0x2d, 0x63, 0x6f, 0x6d, 0x70, 0x61,
            0x73, 0x73, 0x2d, 0x64, 0x65, 0x76, 0x65, 0x6c,
            0x6f, 0x70, 0x6d, 0x65, 0x6e, 0x74, 0x2d, 0x73,
            0x65, 0x63, 0x72, 0x65, 0x74, 0x2d, 0x6b, 0x65,
            0x79
    };

    private final JwtProperties jwtProperties;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String generateToken(ApplicationUser user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("fullName", user.getFullName());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(jwtProperties.getExpirationMinutes(), ChronoUnit.MINUTES)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    public Long extractUserId(String token) {
        Object userId = getClaims(token).get("userId");
        if (userId instanceof Number number) {
            return number.longValue();
        }
        return null;
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = getClaims(token);
            return claims.getSubject() != null && claims.getExpiration() != null && claims.getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(DEFAULT_SECRET_BYTES);
    }
}
