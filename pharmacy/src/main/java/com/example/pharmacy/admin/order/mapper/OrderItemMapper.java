package com.example.pharmacy.admin.order.mapper;

import com.example.pharmacy.admin.order.entity.OrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderItemMapper {

    /**
     * 插入订单项
     *
     * @param orderItem
     */
    @Insert("INSERT INTO order_item (order_id, drug_id, quantity, unit_price, total_price, insurance_covered) " +
            "VALUES (#{orderId}, #{drugId}, #{quantity}, #{unitPrice}, #{totalPrice}, #{insuranceCovered})")
    void insertOrderItem(OrderItem orderItem);

    /**
     * 根据订单id获取订单项
     *
     * @param orderId
     * @return
     */
    @Select("SELECT * FROM order_item WHERE order_id = #{orderId}")
    List<OrderItem> getOrderItemsByOrderId(Long orderId);

}
