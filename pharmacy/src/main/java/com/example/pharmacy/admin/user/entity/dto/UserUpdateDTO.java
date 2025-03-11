package com.example.pharmacy.admin.user.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户更新 DTO")
public class UserUpdateDTO {

    @Schema(description = "用户名", example = "tester")
    private String username;

    @Schema(description = "邮箱", example = "test@example.com")
    private String email;

    @Schema(description = "手机号", example = "13800138000")
    private String phone;

    @Schema(description = "角色", example = "USER")
    private String role;

    @Schema(description = "真实姓名", example = "张三")
    private String name;

    @Schema(description = "医保卡号", example = "420454678213")
    private String insuranceNumber;

    @Schema(description = "用户状态",example = "1")
    private Integer status;
}
