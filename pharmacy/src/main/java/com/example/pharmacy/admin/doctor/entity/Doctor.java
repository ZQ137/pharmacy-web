package com.example.pharmacy.admin.doctor.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "医生实体")
public class Doctor {

    @Schema(description = "医生id",example = "1")
    private Long id;

    @Schema(description = "医生对应的userId",example = "1")
    private Long userId;

    @Schema(description = "执业证书编号",example = "123456789")
    private String licenseNumber;

    @Schema(description = "科室",example = "急诊科")
    private String department;

    @Schema(description = "所在医院",example = "武汉市人民医院")
    private String hospital;

    @Schema(description = "从业年限",example = "10")
    private Integer yearOfExperience;

}
