package com.fly.store.controller;

import com.fly.store.commons.utils.JsonResult;
import com.fly.store.pojo.Order;
import com.fly.store.service.OrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseController{

    @Resource
    private OrderService orderService;

    @RequestMapping("/addOrder")
    public JsonResult<Order> addOrder(Integer aid, Integer[] cid, HttpSession session){
        Order order = orderService.addOrder(aid, getUidFromSession(session), getUsernameFromSession(session), cid);
        return new JsonResult<>(SUCCESS,order);
    }

    @RequestMapping("/getOrder")
    public JsonResult<Order> getOrder(Integer oid){
        Order order = orderService.queryOrder(oid);
        return new JsonResult<>(SUCCESS,order);
    }
}
