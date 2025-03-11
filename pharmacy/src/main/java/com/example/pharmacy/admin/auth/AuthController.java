package com.example.pharmacy.admin.auth;

import com.example.pharmacy.admin.auth.entity.dto.LoginDTO;
import com.example.pharmacy.admin.user.entity.dto.UserRegisterDTO;
import com.example.pharmacy.admin.user.entity.vo.UserVO;
import com.example.pharmacy.common.ErrorCode;
import com.example.pharmacy.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户注册控制器
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "认证控制器")
public class AuthController{

    @Autowired
    private AuthService authService;

    /**
     * 用户注册
     *
     * @param userRegisterDTO
     * @return
     */
    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "用户注册接口")
    public Result<String> register(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        authService.registerUser(userRegisterDTO);
        return Result.success("注册成功");
    }

    /**
     * 用户登录
     *
     * @param loginDTO
     * @return
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录接口")
    public Result<UserVO> login(@RequestBody @Valid LoginDTO loginDTO) {
        UserVO userVO = authService.login(loginDTO);
        return userVO != null ? Result.success(userVO) : Result.error(ErrorCode.USER_NOT_FOUND_OR_PASSWORD_ERROR);
    }
}
