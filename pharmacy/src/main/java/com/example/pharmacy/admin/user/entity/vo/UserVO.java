package com.example.pharmacy.admin.user.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "用户信息 VO")
public class UserVO {

    @Schema(description = "用户ID", example = "1")
    private Long id;

    @Schema(description = "用户名", example = "testuser")
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

    @Schema(description = "创建时间", example = "2025-03-02 20:13:42")
    private LocalDateTime createdAt;

    @Schema(description = "用户状态",example = "1")
    private Integer status;

    @Schema(description = "JWT Token", example = "eyJhbGciOiJIUz...")
    private String token;
}
