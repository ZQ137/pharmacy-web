package com.example.pharmacy.admin.order.mapper;

import com.example.pharmacy.admin.order.entity.Payment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PaymentMapper {

    @Insert("INSERT INTO payments (order_id, payment_method, amount) " +
            "VALUES (#{orderId}, #{paymentMethod}, #{amount})")
    void insertPayment(Payment payment);

    @Select("SELECT * FROM payments WHERE order_id = #{orderId}")
    List<Payment> getPaymentsByOrderId(Long orderId);
}
