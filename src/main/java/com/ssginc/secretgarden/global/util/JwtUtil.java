package com.ssginc.secretgarden.global.util;

import com.ssginc.secretgarden.domain.member.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String key;

    @Value("${jwt.expiration}")
    private long expiration;

    private Key secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Member member) {
        Date now = new Date();
        Date expDate = new Date(now.getTime() + expiration);
        //Header 부분 설정
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        //payload 부분 설정
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("memberId", member.getId());
        payloads.put("BlossomId", member.getBlossomId());
        payloads.put("company",member.getCompany().getName());

        return Jwts.builder()
                .setHeader(headers) // Headers 설정
                .setClaims(payloads)
                .setIssuedAt(now)
                .setExpiration(expDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Integer getMemberIdByToken(String token) {
        String jwtToken = token.substring(7);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(jwtToken)
                .getBody();
        return (Integer) claims.get("memberId");
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}