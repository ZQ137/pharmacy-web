package com.example.pharmacy.admin.user.entity.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户注册 DTO")
public class UserRegisterDTO {

    @Schema(description = "用户名",example = "zhang_san")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "密码",example = "123456")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(description = "邮箱",example = "12435@qq.com")
    @Email(message = "邮箱格式不正确")
    private String email;

    @Schema(description = "手机号",example = "12345678912")
    private String phone;

}
