package com.example.pharmacy.admin.consultation.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "问诊创建 DTO")
public class ConsultationAddDTO {
    private Integer userId;
    private String description;
}
