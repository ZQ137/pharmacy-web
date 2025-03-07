package com.example.pharmacy.entity.dto;

import com.example.pharmacy.entity.orders.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoDTO {
    private Integer id;
    private Integer userId;
    private String status;
    private Timestamp createdAt;
    private Double totalPrice;
    private Double cashPayment;
    private Double insurancePayment;
    private String paymentMethod;
    private List<OrderItem> items;
}
