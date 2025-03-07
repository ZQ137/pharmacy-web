package com.example.pharmacy.controller;

import com.example.pharmacy.entity.dto.OrderInfoDTO;
import com.example.pharmacy.entity.orders.OrderInfo;
import com.example.pharmacy.entity.orders.OrderQueryDTO;
import com.example.pharmacy.entity.orders.OrderVO;
import com.example.pharmacy.entity.request.OrderRequest;
import com.example.pharmacy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
//@CrossOrigin(origins = "*")
public class OderInfoController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public Integer createOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest.getUserId(), orderRequest.getItems(), orderRequest.getPaymentMethod());
    }

    @PostMapping("/list")
    public List<OrderVO> queryOrders(@RequestBody OrderQueryDTO query) {
        return orderService.queryOrders(query);
    }

    @PutMapping("/{id}/status")
    public void updateOrderStatus(@PathVariable Integer id, @RequestParam String status) {
        orderService.updateOrderStatus(id, status);
    }
}
