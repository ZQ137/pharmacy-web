package com.example.pharmacy.admin.user.entity.dto;

import com.example.pharmacy.common.QueryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户查询 DTO")
public class UserQueryDTO extends QueryDTO {

    @Schema(description = "用户名", example = "tester")
    private String username;

    @Schema(description = "角色",example = "DOCTOR")
    private String role;

    @Schema(description = "邮箱",example = "12435@qq.com")
    private String email;

    @Schema(description = "手机号",example = "12345678912")
    private String phone;

    @Schema(description = "用户状态",example = "1")
    private Integer status;

}
