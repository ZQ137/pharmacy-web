package com.example.pharmacy.admin.order.controller;

import com.example.pharmacy.admin.order.entity.dto.OrderQueryDTO;
import com.example.pharmacy.admin.order.entity.vo.OrderVO;
import com.example.pharmacy.admin.order.entity.dto.OrderAddDTO;
import com.example.pharmacy.admin.order.service.OrderService;
import com.example.pharmacy.common.PageResult;
import com.example.pharmacy.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "订单管理")
public class OderController {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     *
     * @param orderAddDTO
     * @return
     */
    @PostMapping("/create")
    @Operation(summary = "创建订单接口",description = "创建订单接口，对接收银系统，后台系统中不应该创建订单")
    public Result<?> createOrder(@RequestBody OrderAddDTO orderAddDTO) {
        orderService.createOrder(orderAddDTO);
        return Result.success("创建成功");
    }

    /**
     * 查询订单
     *
     * @param queryDTO
     * @return
     */
    @PostMapping("/list")
    @Operation(summary = "查询订单",description = "分页条件查询")
    public Result<PageResult<OrderVO>> queryOrders(@RequestBody OrderQueryDTO queryDTO) {
        PageResult<OrderVO> result = orderService.queryOrders(queryDTO);
        return Result.success(result);
    }

    /**
     * 更新订单状态
     *
     * @param id
     * @param status
     */
    @PutMapping("/{id}/status")
    public void updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        orderService.updateOrderStatus(id, status);
    }
}
