package com.example.pharmacy.admin.user.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "用户添加 DTO")
public class UserAddDTO {

    @Schema(description = "用户名", example = "tester")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "密码", example = "123456")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(description = "手机号",example = "12345678912")
    private String phone;

    @Schema(description = "邮箱",example = "12435@qq.com")
    @Email(message = "邮箱格式不正确")
    private String email;

    @Schema(description = "角色",example = "DOCTOR")
    private String role;

    @Schema(description = "真实姓名",example = "张三")
    private String name;

    @Schema(description = "医保卡号",example = "420454678213")
    private String insuranceNumber;

}
