package com.example.pharmacy.admin.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private Long id;          // 订单项ID
    private Long orderId;     // 订单ID
    private Long drugId;      // 药品ID
    private Integer quantity;    // 购买数量
    private Double unitPrice;    // 单价
    private Double totalPrice;   // 该商品总价
    private Integer insuranceCovered; // 是否医保覆盖 1 是 0 否
}
