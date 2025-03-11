package com.example.pharmacy.admin.order.service;

import com.example.pharmacy.admin.order.entity.Order;
import com.example.pharmacy.admin.order.entity.OrderItem;
import com.example.pharmacy.admin.order.entity.Payment;
import com.example.pharmacy.admin.order.entity.dto.OrderAddDTO;
import com.example.pharmacy.admin.order.entity.dto.OrderQueryDTO;
import com.example.pharmacy.admin.order.entity.vo.OrderVO;
import com.example.pharmacy.admin.order.mapper.OrderItemMapper;
import com.example.pharmacy.admin.order.mapper.OrderMapper;
import com.example.pharmacy.admin.order.mapper.PaymentMapper;
import com.example.pharmacy.common.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private PaymentMapper paymentMapper;

    /**
     * 创建订单
     *
     * @param orderAddDTO
     */
    @Transactional
    public void createOrder(OrderAddDTO orderAddDTO) {
        double totalPrice = 0.0;
        double cashPayment = 0.0;
        double insurancePayment = 0.0;

        for (OrderItem item : orderAddDTO.getItems()) {
            double itemTotalPrice = item.getQuantity() * item.getUnitPrice();
            totalPrice += itemTotalPrice;

            if (1 == item.getInsuranceCovered()) {
                insurancePayment += itemTotalPrice;
            } else {
                cashPayment += itemTotalPrice;
            }
        }

        // 订单信息
        Order order = new Order();
        order.setUserId(orderAddDTO.getUserId());
        order.setStatus("PENDING");
        order.setTotalPrice(totalPrice);
        order.setCashPayment(cashPayment);
        order.setInsurancePayment(insurancePayment);
        order.setPaymentMethod(order.getPaymentMethod());
        order.setCreatedAt(LocalDateTime.now());
        orderMapper.insertOrder(order);

        // 插入订单项
        for (OrderItem item : orderAddDTO.getItems()) {
            item.setOrderId(order.getId());
            orderItemMapper.insertOrderItem(item);
        }

        // 记录支付信息
        Payment payment = new Payment();
        payment.setOrderId(order.getId());
        payment.setPaymentMethod(order.getPaymentMethod());
        payment.setAmount(totalPrice);
        paymentMapper.insertPayment(payment);

    }

    /**
     * 查询订单
     *
     * @param query
     * @return
     */
    public PageResult<OrderVO> queryOrders(OrderQueryDTO query) {
        PageHelper.startPage(query.getPage(), query.getSize());

        List<Order> orderList = orderMapper.queryOrders(query);
        List<OrderVO> orderVOList = new ArrayList<>();
        for (Order order : orderList) {
            OrderVO orderVO = new OrderVO();
            // 复制 OrderInfo 的基本信息
            BeanUtils.copyProperties(order, orderVO);

            // 3. 查询订单的 OrderItemList 和 PaymentList
            List<OrderItem> orderItemList = orderItemMapper.getOrderItemsByOrderId(order.getId());
            List<Payment> paymentList = paymentMapper.getPaymentsByOrderId(order.getId());

            // 4. 赋值到 OrderVO
            orderVO.setOrderItemList(orderItemList);
            orderVO.setPaymentList(paymentList);

            // 5. 加入到返回列表
            orderVOList.add(orderVO);
        }
        PageInfo<OrderVO> pageInfo = new PageInfo<>(orderVOList);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getPages(), orderVOList);
    }

    /**
     * 更新订单状态
     *
     * @param orderId
     * @param status
     */
    public void updateOrderStatus(Long orderId, String status) {
        orderMapper.updateOrderStatus(orderId, status);
    }
}