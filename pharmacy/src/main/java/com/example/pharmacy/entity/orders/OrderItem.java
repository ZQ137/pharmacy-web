package com.example.pharmacy.entity.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private Integer id;          // 订单项ID
    private Integer orderId;     // 订单ID
    private Integer drugId;      // 药品ID
    private Integer quantity;    // 购买数量
    private Double unitPrice;    // 单价
    private Double totalPrice;   // 该商品总价
    private Boolean insuranceCovered; // 是否医保覆盖
}
