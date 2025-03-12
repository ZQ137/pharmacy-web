package com.example.pharmacy.admin.consultation.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "问诊状态枚举")
public enum ConsultationStatus {
        PENDING, DIAGNOSED,COMPLETED;
}
