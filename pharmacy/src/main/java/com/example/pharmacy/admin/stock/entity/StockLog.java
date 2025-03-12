package com.example.pharmacy.admin.stock.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "库存变更日志")
public class StockLog {
    private Long id;
    private Long drugId;
    private Integer changeType;
    private Integer changeQuantity;
    private Integer remainingStock;
    private LocalDateTime createdAt;
}
