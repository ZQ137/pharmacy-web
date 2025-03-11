package com.example.pharmacy.admin.order.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "订单实体")
public class Order {

    private Long id;           // 订单ID

    private Long userId;       // 用户ID

    private String status;        // 订单状态

    private LocalDateTime createdAt;  // 创建时间

    private Double totalPrice;    // 订单总金额

    private Double cashPayment;   // 现金支付金额

    private Double insurancePayment; // 医保支付金额

    private String paymentMethod; // 支付方式 (CASH, INSURANCE, MIXED, CREDIT_CARD, WECHAT, ALIPAY)

}
