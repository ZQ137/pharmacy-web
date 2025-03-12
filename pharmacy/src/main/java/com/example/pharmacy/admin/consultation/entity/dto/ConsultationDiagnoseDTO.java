package com.example.pharmacy.admin.consultation.entity.dto;

import com.example.pharmacy.admin.consultation.entity.ConsultationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Schema(description = "医生诊断 DTO")
public class ConsultationDiagnoseDTO {
    private Long id;
    private String diagnosis;
    @Enumerated(EnumType.STRING)
    private ConsultationStatus status;

}
