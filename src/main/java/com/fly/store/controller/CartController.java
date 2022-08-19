package com.fly.store.controller;

import com.fly.store.commons.utils.JsonResult;
import com.fly.store.service.CartService;
import com.fly.store.vo.CartVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController extends BaseController{
    @Resource
    private CartService cartService;

    @RequestMapping("/addCart")
    public JsonResult<Void> addCart(Integer pid, Integer amount, HttpSession session){
        cartService.addCart(getUidFromSession(session),pid,amount,getUsernameFromSession(session));
        return new JsonResult<>(SUCCESS);
    }

    @RequestMapping("/showCart")
    public JsonResult<List<CartVO>> showCart(HttpSession session){
        List<CartVO> cartVOS = cartService.queryCartByUid(getUidFromSession(session));
        return new JsonResult<>(SUCCESS,cartVOS);
    }

    @RequestMapping("/addCartNum")
    public JsonResult<Integer> addCartNum(Integer cid,HttpSession session){
        Integer num =cartService.addNum(cid,getUidFromSession(session),getUsernameFromSession(session));
        return new JsonResult<>(SUCCESS,num);
    }

    @RequestMapping("/reduceCartNum")
    public JsonResult<Integer> reduceCartNum(Integer cid,HttpSession session){
        Integer num =cartService.reduceNum(cid,getUidFromSession(session),getUsernameFromSession(session));
        return new JsonResult<>(SUCCESS,num);
    }

    @RequestMapping("/showPayList")
    public JsonResult<List<CartVO>> showPayList(Integer[] cid){
        List<CartVO> list = cartService.queryCartByCid(cid);
        return new JsonResult<>(SUCCESS,list);
    }

    @RequestMapping("/delCartItem")
    public JsonResult<Void> delCartItem(Integer cid){
        cartService.delCartByCid(cid);
        return new JsonResult<>(SUCCESS);
    }
}
