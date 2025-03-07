package com.example.pharmacy.mapper;

import com.example.pharmacy.entity.orders.OrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    @Insert("INSERT INTO order_item (order_id, drug_id, quantity, unit_price, total_price, insurance_covered) " +
            "VALUES (#{orderId}, #{drugId}, #{quantity}, #{unitPrice}, #{totalPrice}, #{insuranceCovered})")
    void insertOrderItem(OrderItem orderItem);

    @Select("SELECT * FROM order_item WHERE order_id = #{orderId}")
    List<OrderItem> getOrderItemsByOrderId(Integer orderId);
}
