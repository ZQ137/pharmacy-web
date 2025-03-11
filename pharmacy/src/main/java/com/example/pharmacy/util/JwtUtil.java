package com.example.pharmacy.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET_STRING = "mysecretkeymysecretkeymysecretkey123"; // 至少 32 字节
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8)); // ✅ 预处理密钥
    private static final long EXPIRATION_TIME = 86400000; // 24 小时

    // 生成 JWT
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // ✅ 直接用 Key
                .compact();
    }

    // 解析 JWT 获取用户名
    public String getUsernameFromToken(String token) {
        return parseToken(removeBearerPrefix(token)).getBody().getSubject();
    }

    // 解析 JWT 获取角色
    public String getRoleFromToken(String token) {
        return parseToken(removeBearerPrefix(token)).getBody().get("role", String.class);
    }

    // 检查 token 是否过期
    public boolean isTokenExpired(String token) {
        try {
            return parseToken(removeBearerPrefix(token)).getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true; // 过期
        } catch (JwtException e) {
            return false; // 其他错误（格式错误等）
        }
    }

    // 解析 JWT
    private Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token);
    }

    // 自动去掉 "Bearer " 前缀
    private String removeBearerPrefix(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7); // 去掉 "Bearer " 前缀
        }
        return token;
    }
}
