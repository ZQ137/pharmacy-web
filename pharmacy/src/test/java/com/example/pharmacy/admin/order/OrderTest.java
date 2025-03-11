package com.example.pharmacy.admin.order;

import com.example.pharmacy.admin.order.entity.Order;
import com.example.pharmacy.admin.order.entity.OrderItem;
import com.example.pharmacy.admin.order.entity.Payment;
import com.example.pharmacy.admin.order.entity.dto.OrderAddDTO;
import com.example.pharmacy.admin.order.entity.dto.OrderQueryDTO;
import com.example.pharmacy.admin.order.entity.vo.OrderVO;
import com.example.pharmacy.admin.order.mapper.OrderItemMapper;
import com.example.pharmacy.admin.order.mapper.OrderMapper;
import com.example.pharmacy.admin.order.mapper.PaymentMapper;
import com.example.pharmacy.admin.order.service.OrderService;
import com.example.pharmacy.common.PageResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class OrderTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderItemMapper orderItemMapper;

    @Mock
    private PaymentMapper paymentMapper;

    @Test
    void testCreateOrder() {
        // 构造订单 DTO
        OrderAddDTO orderAddDTO = new OrderAddDTO();
        orderAddDTO.setUserId(1L);

        OrderItem item1 = new OrderItem();
        item1.setQuantity(2);
        item1.setUnitPrice(50.0);
        item1.setInsuranceCovered(1);

        OrderItem item2 = new OrderItem();
        item2.setQuantity(1);
        item2.setUnitPrice(100.0);
        item2.setInsuranceCovered(0);

        orderAddDTO.setItems(Arrays.asList(item1, item2));

        // 模拟数据库操作
        Order order = new Order();
        order.setId(1L);
        Mockito.doAnswer(invocation -> {
            ((Order) invocation.getArgument(0)).setId(1L);
            return null;
        }).when(orderMapper).insertOrder(Mockito.any(Order.class));

        // 调用 `createOrder`
        orderService.createOrder(orderAddDTO);

        // 验证插入订单
        Mockito.verify(orderMapper, Mockito.times(1)).insertOrder(Mockito.any(Order.class));

        // 验证插入订单项
        Mockito.verify(orderItemMapper, Mockito.times(2)).insertOrderItem(Mockito.any(OrderItem.class));

        // 验证插入支付信息
        Mockito.verify(paymentMapper, Mockito.times(1)).insertPayment(Mockito.any(Payment.class));
    }

    @Test
    void testQueryOrders() {
        // 构造查询 DTO
        OrderQueryDTO queryDTO = new OrderQueryDTO();
        queryDTO.setPage(1);
        queryDTO.setSize(10);

        // 构造订单数据
        Order order = new Order();
        order.setId(1L);
        order.setUserId(1L);
        order.setTotalPrice(200.0);
        order.setStatus("PENDING");

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(1L);
        orderItem.setQuantity(2);
        orderItem.setUnitPrice(50.0);

        Payment payment = new Payment();
        payment.setOrderId(1L);
        payment.setAmount(200.0);
        payment.setPaymentMethod("CASH");

        // 模拟返回数据
        Mockito.when(orderMapper.queryOrders(queryDTO)).thenReturn(Collections.singletonList(order));
        Mockito.when(orderItemMapper.getOrderItemsByOrderId(1L)).thenReturn(Collections.singletonList(orderItem));
        Mockito.when(paymentMapper.getPaymentsByOrderId(1L)).thenReturn(Collections.singletonList(payment));

        // 调用 `queryOrders`
        PageResult<OrderVO> result = orderService.queryOrders(queryDTO);

        // 断言
        assertNotNull(result);
        assertEquals(1, result.getList().size());
        assertEquals(1L, result.getList().get(0).getId());
        assertEquals(200.0, result.getList().get(0).getTotalPrice());
        assertEquals(1, result.getList().get(0).getOrderItemList().size());
        assertEquals(1, result.getList().get(0).getPaymentList().size());

        // 验证查询订单
        Mockito.verify(orderMapper, Mockito.times(1)).queryOrders(queryDTO);
        Mockito.verify(orderItemMapper, Mockito.times(1)).getOrderItemsByOrderId(1L);
        Mockito.verify(paymentMapper, Mockito.times(1)).getPaymentsByOrderId(1L);
    }

    @Test
    void testUpdateOrderStatus() {
        Long orderId = 1L;
        String newStatus = "COMPLETED";

        // 调用 `updateOrderStatus`
        orderService.updateOrderStatus(orderId, newStatus);

        // 验证 update 方法是否被调用
        Mockito.verify(orderMapper, Mockito.times(1)).updateOrderStatus(orderId, newStatus);
    }


}
