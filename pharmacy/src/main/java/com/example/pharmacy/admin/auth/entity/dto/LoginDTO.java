package com.example.pharmacy.admin.auth.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "用户登录请求")
public class LoginDTO {

    @Schema(description = "用户名",example = "testuser")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "密码",example = "password123")
    @NotBlank(message = "密码不能为空")
    private String password;

}
