package com.aigroup.world.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class ShareLinkUtil {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 加密用户ID
     * @param userId 用户ID
     * @return 加密后的token
     */
    public String encryptUserId(Long userId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .signWith(jwtUtil.getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 解密用户ID
     * @param token 加密后的token
     * @return 用户ID
     */
    public Long decryptUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtUtil.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }
} 