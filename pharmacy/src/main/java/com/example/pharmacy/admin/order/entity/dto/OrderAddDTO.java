package com.example.pharmacy.admin.order.entity.dto;

import com.example.pharmacy.admin.order.entity.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "创建订单 DTO")
public class OrderAddDTO {

    @Schema(description = "用户ID",example = "1")
    private Long userId;

    @Schema(description = "订单项")
    private List<OrderItem> items;

    @Schema(description = "支付方式")
    private String paymentMethod;
}
