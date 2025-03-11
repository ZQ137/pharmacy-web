package com.example.pharmacy.admin.order.mapper;

import com.example.pharmacy.admin.order.entity.Order;
import com.example.pharmacy.admin.order.entity.dto.OrderQueryDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {

    /**
     * 插入订单
     *
     * @param order
     */
    @Insert("INSERT INTO order (user_id, status, total_price, cash_payment, insurance_payment, payment_method,created_at) " +
            "VALUES (#{userId}, 'PENDING', #{totalPrice}, #{cashPayment}, #{insurancePayment}, #{paymentMethod},#{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertOrder(Order order);

    /**
     * 查询订单
     *
     * @param query
     * @return
     */
    List<Order> queryOrders(@Param("query") OrderQueryDTO query);

    /**
     * 更新订单状态
     *
     * @param id
     * @param status
     */
    @Update("UPDATE order SET status = #{status} WHERE id = #{id}")
    void updateOrderStatus(@Param("id") Long id, @Param("status") String status);
}
