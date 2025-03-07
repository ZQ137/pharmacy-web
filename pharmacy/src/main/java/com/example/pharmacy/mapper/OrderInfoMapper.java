package com.example.pharmacy.mapper;

import com.example.pharmacy.entity.orders.OrderInfo;
import com.example.pharmacy.entity.orders.OrderQueryDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderInfoMapper {
    @Insert("INSERT INTO order_info (user_id, status, total_price, cash_payment, insurance_payment, payment_method) " +
            "VALUES (#{userId}, 'PENDING', #{totalPrice}, #{cashPayment}, #{insurancePayment}, #{paymentMethod})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertOrder(OrderInfo order);

    List<OrderInfo> queryOrders(@Param("query") OrderQueryDTO query);

    @Update("UPDATE order_info SET status = #{status} WHERE id = #{id}")
    void updateOrderStatus(@Param("id") Integer id, @Param("status") String status);
}
