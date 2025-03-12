package com.example.pharmacy.admin.stock.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "库存调整 DTO")
public class StockAdjustmentDTO {
    private Long drugId;
    private Integer changeType;
    private Integer changeQuantity;
}
