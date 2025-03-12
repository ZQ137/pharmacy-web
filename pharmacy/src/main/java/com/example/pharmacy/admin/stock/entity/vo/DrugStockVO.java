package com.example.pharmacy.admin.stock.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "药品库存 VO")
public class DrugStockVO {
    private Long drugId;
    private String name;
    private Integer stock;
    private LocalDateTime updatedAt;
}
