package com.example.pharmacy.entity.orders;

import lombok.Data;

import java.util.List;

@Data
public class OrderQueryDTO {
    private String id;
    private String userId;
    private String status;
    private String paymentMethod;
    private List<String> dateRange;
}
