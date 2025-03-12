package com.example.pharmacy.admin.user;

import com.example.pharmacy.admin.user.entity.User;
import com.example.pharmacy.admin.user.entity.dto.UserAddDTO;
import com.example.pharmacy.admin.user.entity.dto.UserQueryDTO;
import com.example.pharmacy.admin.user.entity.dto.UserUpdateDTO;
import com.example.pharmacy.admin.user.entity.vo.UserVO;
import com.example.pharmacy.admin.user.mapper.UserMapper;
import com.example.pharmacy.admin.user.service.UserService;
import com.example.pharmacy.common.ErrorCode;
import com.example.pharmacy.common.PageResult;
import com.example.pharmacy.exception.CustomException;
import com.example.pharmacy.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserTest {
    @Mock
    private UserMapper userMapper;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testUser");
        testUser.setPassword("encryptedPassword");
        testUser.setEmail("test@example.com");
        testUser.setRole("USER");
    }

    /** 测试获取所有用户 */
    @Test
    void testGetAllUsers() {
        UserQueryDTO queryDTO = new UserQueryDTO();
        queryDTO.setUsername("testUser");
        queryDTO.setPage(1);
        queryDTO.setSize(10);

        List<User> users = Arrays.asList(testUser);

        when(userMapper.findUsersByConditions(queryDTO)).thenReturn(users);

        PageResult<UserVO> userVOList = userService.getAllUsers(queryDTO);

        assertNotNull(userVOList);
        assertEquals(1, userVOList.getList().size());
        assertEquals(testUser.getUsername(), userVOList.getList().get(0).getUsername());

        verify(userMapper, times(1)).findUsersByConditions(queryDTO);
    }

    /** 测试更新用户信息 */
    @Test
    void testUpdateUser_Success() {
        UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setEmail("updated@example.com");

        when(jwtUtil.getUsernameFromToken("validToken")).thenReturn("testUser");
        when(userMapper.findByUsername("testUser")).thenReturn(Optional.of(testUser));

        userService.updateUser("validToken", updateDTO);

        verify(userMapper, times(1)).updateUser(Mockito.any(User.class));
    }

    /** 测试更新用户信息 - 用户不存在 */
    @Test
    void testUpdateUser_UserNotFound() {
        when(jwtUtil.getUsernameFromToken("validToken")).thenReturn("testUser");
        when(userMapper.findByUsername("testUser")).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () ->
                userService.updateUser("validToken", new UserUpdateDTO())
        );

        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    /** 测试删除用户 */
    @Test
    void testDeleteUser() {
        userService.deleteUser(1L);
        verify(userMapper, times(1)).deleteUser(1L);
    }

    /** 测试获取用户信息 */
    @Test
    void testGetUserInfo_Success() {
        when(userMapper.findByUsername("testUser")).thenReturn(Optional.of(testUser));

        UserVO userVO = userService.getUserInfo("testUser");

        assertNotNull(userVO);
        assertEquals(testUser.getUsername(), userVO.getUsername());
    }

    /** 测试获取用户信息 - 用户不存在 */
    @Test
    void testGetUserInfo_UserNotFound() {
        when(userMapper.findByUsername("testUser")).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () ->
                userService.getUserInfo("testUser")
        );

        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    /** 测试添加用户 */
    @Test
    void testAddUser_Success() {
        UserAddDTO userAddDTO = new UserAddDTO();
        userAddDTO.setUsername("newUser");
        userAddDTO.setEmail("new@example.com");

        when(jwtUtil.getUsernameFromToken("validToken")).thenReturn("testUser");
        when(userMapper.findByUsername("testUser")).thenReturn(Optional.of(testUser));

        userService.addUser("validToken", userAddDTO);

        verify(userMapper, times(1)).insertUser(Mockito.any(User.class));
    }

    /** 测试添加用户 - 当前用户不存在 */
    @Test
    void testAddUser_UserNotFound() {
        when(jwtUtil.getUsernameFromToken("validToken")).thenReturn("testUser");
        when(userMapper.findByUsername("testUser")).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () ->
                userService.addUser("validToken", new UserAddDTO())
        );

        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }
}
