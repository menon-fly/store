package com.fly.store.service.impl;

import com.fly.store.commons.utils.UUIDUtils;
import com.fly.store.controller.ex.DataNotFoundException;
import com.fly.store.mapper.CartMapper;
import com.fly.store.mapper.ProductMapper;
import com.fly.store.pojo.Cart;
import com.fly.store.pojo.Product;
import com.fly.store.service.CartService;
import com.fly.store.service.ex.CartNotFoundException;
import com.fly.store.service.ex.DeleteException;
import com.fly.store.service.ex.InsertException;
import com.fly.store.service.ex.UpdateException;
import com.fly.store.vo.CartVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;


@Service
public class CartServiceImpl implements CartService {
    @Resource
    private CartMapper cartMapper;
    @Resource
    private ProductMapper productMapper;
    @Override
    public void addCart(Integer uid, Integer pid, Integer amount, String username) {
        Cart result = cartMapper.selectByUidAndPid(uid,pid);
        if (result == null){
            Cart cart = new Cart();
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);
            Product product = productMapper.selectById(pid);
            cart.setPrice(product.getPrice());
            cart.setCreatedUser(username);
            cart.setCreatedTime(new Date());
            cart.setModifiedUser(username);
            cart.setModifiedTime(new Date());
            Integer rows = cartMapper.insertCart(cart);
            if (rows != 1){
                throw new InsertException("增加商品发生异常");
            }
        }else {
            Integer num = result.getNum() + amount;
            Integer rows = cartMapper.updateNumByCid(result.getCid(),num,username,new Date());
            if (rows != 1){
                throw new UpdateException("加入购物车发生异常");
            }
        }
    }

    @Override
    public List<CartVO> queryCartByUid(Integer uid) {
        return cartMapper.selectVOByUid(uid);
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        Cart ret = cartMapper.selectCart(cid);
        if (ret == null){
            throw new CartNotFoundException("商品不存在");
        }
        Integer num = ret.getNum() + 1;
        Integer rows = cartMapper.updateNumByCid(cid,num,username,new Date());
        if (rows != 1){
            throw new UpdateException("更新失败");
        }
        return num;
    }
    @Override
    public Integer reduceNum(Integer cid, Integer uid, String username) {
        Cart ret = cartMapper.selectCart(cid);
        if (ret == null){
            throw new CartNotFoundException("商品不存在");
        }
        Integer num = ret.getNum() - 1;
        Integer rows = cartMapper.updateNumByCid(cid,num,username,new Date());
        if (rows != 1){
            throw new UpdateException("更新失败");
        }
        return num;
    }

    @Override
    public List<CartVO> queryCartByCid(Integer[] cid) {
        List<CartVO> cartVOS = cartMapper.selectVOByCid(cid);
        return cartVOS;
    }

    @Override
    public void delCartByCid(Integer cid) {
        Integer rows = cartMapper.deleteCartByCid(cid);
        if (rows != 1){
            throw new DeleteException("删除发生异常");
        }
    }
}
