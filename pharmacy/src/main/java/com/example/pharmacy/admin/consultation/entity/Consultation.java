package com.example.pharmacy.admin.consultation.entity;

import com.example.pharmacy.admin.consultation.entity.dto.ConsultationDiagnoseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "问诊实体")
public class Consultation {
    private Integer id;
    private Integer userId;
    private Integer doctorId;

    @Enumerated(EnumType.STRING)
    private ConsultationStatus status;

    private String description;
    private String diagnosis;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
