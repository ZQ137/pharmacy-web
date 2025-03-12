package com.example.pharmacy.admin.consultation.entity.vo;

import com.example.pharmacy.admin.consultation.entity.ConsultationStatus;
import com.example.pharmacy.admin.consultation.entity.dto.ConsultationDiagnoseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "问诊 VO")
public class ConsultationVO {
    private Integer id;
    private Integer userId;
    private Integer doctorId;
    @Enumerated(EnumType.STRING)
    private ConsultationStatus status;
    private String description;
    private String diagnosis;
    private LocalDateTime createdAt;
}
