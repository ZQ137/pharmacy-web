package com.example.pharmacy.admin.doctor.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "医生视图对象")
public class DoctorVO {

    @Schema(description = "用户名", example = "doctor123")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "姓名", example = "张三")
    @NotBlank(message = "姓名不能为空")
    private String name;

    @Schema(description = "邮箱", example = "doctor@example.com")
    private String email;

    @Schema(description = "手机号", example = "13800000000")
    private String phone;

    @Schema(description = "执业证书编号", example = "A12345678")
    @NotBlank(message = "执业证书编号不能为空")
    private String licenseNumber;

    @Schema(description = "科室", example = "内科")
    @NotBlank(message = "科室不能为空")
    private String department;

    @Schema(description = "所在医院", example = "XX医院")
    private String hospital;

    @Schema(description = "从业年限", example = "10")
    private Integer yearsOfExperience;

}
