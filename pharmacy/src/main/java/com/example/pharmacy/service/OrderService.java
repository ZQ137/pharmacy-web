package com.example.pharmacy.service;

import com.example.pharmacy.entity.orders.*;
import com.example.pharmacy.mapper.OrderInfoMapper;
import com.example.pharmacy.mapper.OrderItemMapper;
import com.example.pharmacy.mapper.PaymentMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private PaymentMapper paymentMapper;

    @Transactional
    public Integer createOrder(Integer userId, List<OrderItem> items, String paymentMethod) {
        double totalPrice = 0.0;
        double cashPayment = 0.0;
        double insurancePayment = 0.0;

        for (OrderItem item : items) {
            double itemTotalPrice = item.getQuantity() * item.getUnitPrice();
            totalPrice += itemTotalPrice;

            if (item.getInsuranceCovered()) {
                insurancePayment += itemTotalPrice;
            } else {
                cashPayment += itemTotalPrice;
            }
        }

        // 订单信息
        OrderInfo order = new OrderInfo();
        order.setUserId(userId);
        order.setStatus("PENDING");
        order.setTotalPrice(totalPrice);
        order.setCashPayment(cashPayment);
        order.setInsurancePayment(insurancePayment);
        order.setPaymentMethod(paymentMethod);
        orderInfoMapper.insertOrder(order);

        // 插入订单项
        for (OrderItem item : items) {
            item.setOrderId(order.getId());
            orderItemMapper.insertOrderItem(item);
        }

        // 记录支付信息
        Payment payment = new Payment();
        payment.setOrderId(order.getId());
        payment.setPaymentMethod(paymentMethod);
        payment.setAmount(totalPrice);
        paymentMapper.insertPayment(payment);

        return order.getId();
    }

    public List<OrderVO> queryOrders(OrderQueryDTO query) {
        List<OrderInfo> orderInfoList = orderInfoMapper.queryOrders(query);
        List<OrderVO> orderVOList = new ArrayList<>();
        for (OrderInfo orderInfo : orderInfoList) {
            OrderVO orderVO = new OrderVO();
            // 复制 OrderInfo 的基本信息
            BeanUtils.copyProperties(orderInfo, orderVO);

            // 3. 查询订单的 OrderItemList 和 PaymentList
            List<OrderItem> orderItemList = orderItemMapper.getOrderItemsByOrderId(orderInfo.getId());
            List<Payment> paymentList = paymentMapper.getPaymentsByOrderId(orderInfo.getId());
            System.out.println(paymentList);

            // 4. 赋值到 OrderVO
            orderVO.setOrderItemList(orderItemList);
            orderVO.setPaymentList(paymentList);

            // 5. 加入到返回列表
            orderVOList.add(orderVO);
        }
        return orderVOList;
    }

    public void updateOrderStatus(Integer orderId, String status) {
        orderInfoMapper.updateOrderStatus(orderId, status);
    }
}