package com.example.pharmacy.admin.user.controller;

import com.example.pharmacy.admin.user.entity.dto.UserAddDTO;
import com.example.pharmacy.admin.user.entity.dto.UserQueryDTO;
import com.example.pharmacy.admin.user.entity.dto.UserUpdateDTO;
import com.example.pharmacy.admin.user.entity.vo.UserVO;
import com.example.pharmacy.admin.user.service.UserService;
import com.example.pharmacy.common.PageResult;
import com.example.pharmacy.common.Result;
import com.example.pharmacy.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理控制器
 */
@RestController
@RequestMapping("/api/user")
@Tag(name = "用户管理")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 获取所有用户（仅管理员）
     *
     * @return
     */
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "获取所有用户", description = "获取所有用户，仅限管理员查看")
    public Result<PageResult<UserVO>> getAllUsers(UserQueryDTO queryDTO) {
        PageResult<UserVO> result = userService.getAllUsers(queryDTO);
        return Result.success(result);
    }

    /**
     * 获取用户信息
     *
     * @param token
     * @return
     */
    @GetMapping("/info")
    @Operation(summary = "获取用户信息", description = "获取用户信息接口，仅获取当前登录用户的信息")
    public Result<UserVO> getUserInfo(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.getUsernameFromToken(token);
        UserVO userVO = userService.getUserInfo(username);
        return Result.success(userVO);
    }

    /**
     * 添加用户
     *
     * @param userAddDTO
     * @return
     */
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "添加用户", description = "添加用户，仅限管理员")
    public Result<?> addUser(@RequestHeader("Authorization") String token, @RequestBody UserAddDTO userAddDTO) {
        userService.addUser(token, userAddDTO);
        return Result.success("用户已添加");
    }

    /**
     * 更新用户信息
     *
     * @param token
     * @param userUpdateDTO
     * @return
     */
    @PutMapping("/update")
    @Operation(summary = "更新用户信息", description = "更新用户信息")
    public Result<?> updateUser(@RequestHeader("Authorization") String token, @RequestBody UserUpdateDTO userUpdateDTO) {
        userService.updateUser(token, userUpdateDTO);
        return Result.success("用户信息已更新");
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "删除用户", description = "删除用户")
    public Result<?> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return Result.success("用户删除成功");
    }

}
