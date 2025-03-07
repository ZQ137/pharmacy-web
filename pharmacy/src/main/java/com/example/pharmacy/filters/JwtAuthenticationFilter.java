package com.example.pharmacy.filters;

import com.example.pharmacy.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@WebFilter("/*")
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // 去掉 "Bearer " 前缀

            try {
                if (!JwtUtil.isTokenExpired(token)) {
                    String username = JwtUtil.getUsernameFromToken(token);
                    if (username != null) {
                        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(username, null, authorities);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token 已过期");
                    return;
                }
            } catch (Exception e) {
                System.out.println("Token 解析失败: " + e.getMessage()); // 捕获异常并打印
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "无效的 Token");
                return; // 处理异常，例如 token 解析失败等
            }
        }

        filterChain.doFilter(request, response);
    }
}
