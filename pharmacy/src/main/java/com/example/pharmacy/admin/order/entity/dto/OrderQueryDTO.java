package com.example.pharmacy.admin.order.entity.dto;

import com.example.pharmacy.common.QueryDTO;
import lombok.Data;

import java.util.List;

@Data
public class OrderQueryDTO extends QueryDTO {
    private Long id;
    private Long userId;
    private String status;
    private String paymentMethod;
    private List<String> dateRange;
}
