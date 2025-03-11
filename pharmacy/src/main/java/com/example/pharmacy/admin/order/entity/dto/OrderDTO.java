package com.example.pharmacy.admin.order.entity.dto;

import com.example.pharmacy.admin.order.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private Long userId;
    private String status;
    private LocalDateTime createdAt;
    private Double totalPrice;
    private Double cashPayment;
    private Double insurancePayment;
    private String paymentMethod;
    private List<OrderItem> items;
}
