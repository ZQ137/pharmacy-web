package com.example.pharmacy.admin.auth;

import com.example.pharmacy.admin.auth.entity.dto.LoginDTO;
import com.example.pharmacy.admin.user.entity.User;
import com.example.pharmacy.admin.user.entity.dto.UserRegisterDTO;
import com.example.pharmacy.admin.user.entity.vo.UserVO;
import com.example.pharmacy.admin.user.mapper.UserMapper;
import com.example.pharmacy.common.ErrorCode;
import com.example.pharmacy.exception.CustomException;
import com.example.pharmacy.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AuthTest {

    @InjectMocks
    private AuthService authService; // 需要测试的 Service

    @Mock
    private UserMapper userMapper; // 模拟数据库

    @Mock
    private PasswordEncoder passwordEncoder; // 模拟密码加密

    @Mock
    private JwtUtil jwtUtil; // 模拟 JWT 生成

    @Test
    public void testRegisterUser_WhenUserAlreadyExists() {
        // 模拟：用户已存在
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("testUser");

        Mockito.when(userMapper.findByUsername("testUser"))
                .thenReturn(Optional.of(new User()));

        // 断言：应该抛出异常
        CustomException exception = assertThrows(CustomException.class, () -> {
            authService.registerUser(userRegisterDTO);
        });

        assertEquals(ErrorCode.USER_ALREADY_EXISTS, exception.getErrorCode());
    }

    @Test
    public void testRegisterUser_Success() {
        // 模拟：用户不存在
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("newUser");
        userRegisterDTO.setPassword("123456");

        Mockito.when(userMapper.findByUsername("newUser"))
                .thenReturn(Optional.empty());

        Mockito.when(passwordEncoder.encode("123456"))
                .thenReturn("encodedPassword");

        // 执行注册
        authService.registerUser(userRegisterDTO);

        // 验证 insertUser() 被调用
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userMapper).insertUser(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("newUser", savedUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals("PATIENT", savedUser.getRole()); // 角色应为 "PATIENT"
    }

    @Test
    public void testLogin_WhenUserNotFound() {
        // 模拟：用户不存在
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("nonExistingUser");
        loginDTO.setPassword("password");

        Mockito.when(userMapper.findByUsername("nonExistingUser"))
                .thenReturn(Optional.empty());

        // 断言：应该抛出异常
        CustomException exception = assertThrows(CustomException.class, () -> {
            authService.login(loginDTO);
        });

        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    public void testLogin_WhenPasswordIncorrect() {
        // 模拟：用户存在但密码错误
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("testUser");
        loginDTO.setPassword("wrongPassword");

        User user = new User();
        user.setUsername("testUser");
        user.setPassword("encodedPassword");

        Mockito.when(userMapper.findByUsername("testUser"))
                .thenReturn(Optional.of(user));

        Mockito.when(passwordEncoder.matches("wrongPassword", "encodedPassword"))
                .thenReturn(false);

        // 断言：应该抛出异常
        CustomException exception = assertThrows(CustomException.class, () -> {
            authService.login(loginDTO);
        });

        assertEquals(ErrorCode.PASSWORD_ERROR, exception.getErrorCode());
    }

    @Test
    public void testLogin_Success() {
        // 模拟：正确的用户和密码
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("testUser");
        loginDTO.setPassword("correctPassword");

        User user = new User();
        user.setUsername("testUser");
        user.setPassword("encodedPassword");
        user.setRole("PATIENT");

        Mockito.when(userMapper.findByUsername("testUser"))
                .thenReturn(Optional.of(user));

        Mockito.when(passwordEncoder.matches("correctPassword", "encodedPassword"))
                .thenReturn(true);

        Mockito.when(jwtUtil.generateToken("testUser", "PATIENT"))
                .thenReturn("mocked-jwt-token");

        // 执行登录
        UserVO result = authService.login(loginDTO);

        // 断言
        assertEquals("testUser", result.getUsername());
        assertEquals("mocked-jwt-token", result.getToken());
    }
}
