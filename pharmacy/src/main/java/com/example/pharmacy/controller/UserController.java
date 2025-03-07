package com.example.pharmacy.controller;

import com.example.pharmacy.entity.User;
import com.example.pharmacy.service.UserService;
import com.example.pharmacy.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 用户注册
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userService.getUserByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("用户名已存在");
        }
        // 确保 role 不能为 null，普通用户注册时默认为 "USER"
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER"); // 只允许注册普通用户
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // 加密密码
        userService.registerUser(user);
        return ResponseEntity.ok("注册成功");
    }

    // 用户登录
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        Optional<User> userOpt = userService.getUserByUsername(loginRequest.get("username"));
        if (userOpt.isEmpty() || !passwordEncoder.matches(loginRequest.get("password"), userOpt.get().getPassword())) {
            return ResponseEntity.badRequest().body("用户名或密码错误");
        }
        String token = jwtUtil.generateToken(userOpt.get().getUsername());
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    // 获取用户信息
    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.getUsernameFromToken(token.replace("Bearer ", ""));
        Optional<User> userOpt = userService.getUserByUsername(username);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());  // 返回用户信息
        } else {
            return ResponseEntity.badRequest().body("用户不存在");  // 返回错误信息
        }
    }

    // 获取所有用户（仅管理员）
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // 更新用户信息
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String token, @RequestBody User updatedUser) {
        String username = jwtUtil.getUsernameFromToken(token.replace("Bearer ", ""));
        Optional<User> userOpt = userService.getUserByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("用户不存在");
        }
        User user = userOpt.get();
        user.setEmail(updatedUser.getEmail());
        user.setPhone(updatedUser.getPhone());
        userService.updateUser(user);
        return ResponseEntity.ok("用户信息已更新");
    }

    // 删除用户
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("用户删除成功");
    }

}
