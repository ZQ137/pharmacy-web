package com.example.pharmacy.entity.request;

import com.example.pharmacy.entity.orders.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Integer userId;
    private List<OrderItem> items;
    private String paymentMethod; // 支付方式
}
