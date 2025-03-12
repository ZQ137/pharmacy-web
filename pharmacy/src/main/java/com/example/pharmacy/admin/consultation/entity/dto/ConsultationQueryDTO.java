package com.example.pharmacy.admin.consultation.entity.dto;

import com.example.pharmacy.admin.consultation.entity.ConsultationStatus;
import com.example.pharmacy.common.QueryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "问诊查询 DTO")
public class ConsultationQueryDTO extends QueryDTO {
    private Long userId;
    private Long doctorId;

    @Enumerated(EnumType.STRING)
    private ConsultationStatus status;
}
