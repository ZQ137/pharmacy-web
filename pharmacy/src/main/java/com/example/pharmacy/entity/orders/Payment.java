package com.example.pharmacy.entity.orders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private Integer id;
    private Integer orderId;
    private String paymentMethod;
    private Double amount;
    private Timestamp createdAt;
}
