package com.example.pharmacy.admin.order.entity.vo;

import com.example.pharmacy.admin.order.entity.OrderItem;
import com.example.pharmacy.admin.order.entity.Payment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "订单信息 VO")
public class OrderVO {

    @Schema(description = "订单ID",example = "1")
    private Long id;

    @Schema(description = "用户",example = "1")
    private Long userId;

    @Schema(description = "订单状态",example = "1")
    private String status;

    @Schema(description = "创建时间",example = "2025-03-03 20:13:42")
    private LocalDateTime createdAt;

    @Schema(description = "订单总金额",example = "99.99")
    private Double totalPrice;

    @Schema(description = "现金支付金额",example = "50.00")
    private Double cashPayment;

    @Schema(description = "医保支付金额",example = "49.99")
    private Double insurancePayment;

    @Schema(description = "支付方式",example = "CASH")
    private String paymentMethod; // 支付方式 (CASH, INSURANCE, MIXED, CREDIT_CARD, WECHAT, ALIPAY)

    @Schema(description = "订单项")
    private List<OrderItem> orderItemList;

    @Schema(description = "支付记录")
    private List<Payment> paymentList;
}
