package com.example.pharmacy.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // ✅ 直接用 Key
                .compact();
    }

    // 解析 JWT 获取用户名
    public static String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // ✅ 使用 Key
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 检查 token 是否过期
    public static boolean isTokenExpired(String token) {
        try {
            Date expiration = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY) // ✅ 使用 Key
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return true; // Token 过期
        } catch (JwtException e) {
            return false; // 其他异常（例如 Token 格式错误）
        }
    }
}
