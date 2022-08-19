package com.fly.store.mapper;

import com.fly.store.pojo.Order;
import com.fly.store.pojo.OrderItem;

public interface OrderMapper {
    /**
     * 插入订单数据
     * @param order
     * @return
     */
    Integer insertOrder(Order order);

    /**
     * 插入订单项数据
     * @param orderItem
     * @return
     */
    Integer insertOrderItem(OrderItem orderItem);

    Order selectOrderByOid(Integer oid);
}
