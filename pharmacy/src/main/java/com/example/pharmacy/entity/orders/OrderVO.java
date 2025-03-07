package com.example.pharmacy.entity.orders;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class OrderVO {
    private Integer id;           // 订单ID
    private Integer userId;       // 用户ID
    private String status;        // 订单状态
    private Timestamp createdAt;  // 创建时间
    private Double totalPrice;    // 订单总金额
    private Double cashPayment;   // 现金支付金额
    private Double insurancePayment; // 医保支付金额
    private String paymentMethod; // 支付方式 (CASH, INSURANCE, MIXED, CREDIT_CARD, WECHAT, ALIPAY)
    private List<OrderItem> orderItemList;
    private List<Payment> paymentList;
}
