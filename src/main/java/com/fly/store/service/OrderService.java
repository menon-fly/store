package com.fly.store.service;


import com.fly.store.pojo.Order;

public interface OrderService {
    Order addOrder(Integer aid, Integer uid, String username, Integer[] cid);

    Order queryOrder(Integer oid);
}
