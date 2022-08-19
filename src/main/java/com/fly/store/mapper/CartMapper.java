package com.fly.store.mapper;

import com.fly.store.pojo.Cart;
import com.fly.store.vo.CartVO;

import java.util.Date;
import java.util.List;

public interface CartMapper {
    /**
     * 商品加入购物车
     * @param cart
     * @return
     */
    Integer insertCart(Cart cart);

    /**
     * 更新购物车商品数量
     * @param cid
     * @param num
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    Integer updateNumByCid(Integer cid, Integer num, String modifiedUser, Date modifiedTime);

    Cart selectByUidAndPid(Integer uid,Integer pid);

    List<CartVO> selectVOByUid(Integer uid);

    Cart selectCart(Integer cid);

    List<CartVO> selectVOByCid(Integer[] cid);

    Integer deleteCartByCid(Integer cid);
}
