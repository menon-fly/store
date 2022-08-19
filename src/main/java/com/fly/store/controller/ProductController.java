package com.fly.store.controller;

import com.fly.store.commons.utils.JsonResult;
import com.fly.store.pojo.Product;
import com.fly.store.service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController extends BaseController{
    @Resource
    private ProductService productService;

    @RequestMapping("/hotList")
    public JsonResult<List<Product>> hotList(){
        List<Product> products = productService.queryHotProduct();
        return new JsonResult<>(SUCCESS,products);
    }

    @RequestMapping("detail")
    public JsonResult<Product> getDetailById(Integer id){
        Product product = productService.queryById(id);
        return new JsonResult<>(SUCCESS,product);
    }
}
