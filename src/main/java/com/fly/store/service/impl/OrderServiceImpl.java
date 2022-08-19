package com.fly.store.service.impl;

import com.fly.store.controller.ex.DataNotFoundException;
import com.fly.store.mapper.AddressMapper;
import com.fly.store.mapper.CartMapper;
import com.fly.store.mapper.OrderMapper;
import com.fly.store.pojo.Address;
import com.fly.store.pojo.Order;
import com.fly.store.pojo.OrderItem;
import com.fly.store.service.AddressService;
import com.fly.store.service.CartService;
import com.fly.store.service.OrderService;
import com.fly.store.service.ex.InsertException;
import com.fly.store.vo.CartVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private AddressService addressService;
    @Resource
    private CartService cartService;
    @Override
    public Order addOrder(Integer aid, Integer uid, String username, Integer[] cid) {
        List<CartVO> cartVOS = cartService.queryCartByCid(cid);
        Long totalPrice = 0L;
        for (CartVO c : cartVOS){
            totalPrice += c.getRealPrice() * c.getNum();
        }
        Address address = addressService.queryByAid(aid,uid);
        Order order = new Order();
        order.setUid(uid);
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddress());
        order.setStatus(0);
        order.setTotalPrice(totalPrice);
        order.setOrderTime(new Date());
        order.setCreatedTime(new Date());
        order.setCreatedUser(username);
        order.setModifiedTime(new Date());
        order.setModifiedUser(username);
        Integer rows = orderMapper.insertOrder(order);
        if (rows != 1){
            throw new InsertException("添加订单发生异常");
        }
        for (CartVO c : cartVOS){
            OrderItem orderItem = new OrderItem();
            orderItem.setOid(order.getOid());
            orderItem.setPid(c.getPid());
            orderItem.setTitle(c.getTitle());
            orderItem.setImage(c.getImage());
            orderItem.setPrice(c.getPrice());
            orderItem.setNum(c.getNum());
            orderItem.setCreatedTime(new Date());
            orderItem.setCreatedUser(username);
            orderItem.setModifiedTime(new Date());
            orderItem.setModifiedUser(username);
            rows = orderMapper.insertOrderItem(orderItem);
            if (rows != 1){
                throw new InsertException("插入数据异常");
            }
        }
        return order;
    }

    @Override
    public Order queryOrder(Integer oid) {
        Order order = orderMapper.selectOrderByOid(oid);
        if (order == null){
            throw new DataNotFoundException("数据不存在");
        }
        return order;
    }
}
