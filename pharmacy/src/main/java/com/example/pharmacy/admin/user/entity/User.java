package com.example.pharmacy.admin.user.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户实体")
public class User {

    @Schema(description = "用户ID",example = "1")
    private Long id;

    @Schema(description = "用户名", example = "tester")
    private String username;

    @Schema(description = "密码", example = "123456")
    private String password;

    @Schema(description = "邮箱",example = "12435@qq.com")
    private String email;

    @Schema(description = "手机号",example = "12345678912")
    private String phone;

    @Schema(description = "角色",example = "DOCTOR")
    private String role;

    @Schema(description = "真实姓名",example = "张三")
    private String name;

    @Schema(description = "医保卡号",example = "420454678213")
    private String insuranceNumber;

    @Schema(description = "状态",example = "1")
    private Integer status;

    @Schema(description = "创建时间",example = "2025-03-02 20:13:42")
    private LocalDateTime createdAt;

}
