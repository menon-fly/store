package com.fly.store.service;

import com.fly.store.pojo.Cart;
import com.fly.store.vo.CartVO;

import java.util.List;

public interface CartService {
    void addCart(Integer uid,Integer pid,Integer amount,String username);

    List<CartVO> queryCartByUid(Integer uid);

    Integer addNum(Integer cid,Integer uid,String username);

    Integer reduceNum(Integer cid,Integer uid,String username);

    List<CartVO> queryCartByCid(Integer[] cid);

    void delCartByCid(Integer cid);
}
