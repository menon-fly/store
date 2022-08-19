package com.fly.store.service.impl;

import com.fly.store.mapper.ProductMapper;
import com.fly.store.pojo.Product;
import com.fly.store.service.ProductService;
import com.fly.store.service.ex.ProductNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;
    @Override
    public List<Product> queryHotProduct() {
        List<Product> products = productMapper.selectHotProduct();
        for (Product p : products){
            p.setPriority(null);
            p.setCreatedTime(null);
            p.setCreatedUser(null);
            p.setModifiedTime(null);
            p.setModifiedUser(null);
        }
        return products;
    }

    @Override
    public Product queryById(Integer id) {
        Product product = productMapper.selectById(id);
        if (product == null){
            throw new ProductNotFoundException("商品不存在");
        }
        product.setPriority(null);
        product.setCreatedTime(null);
        product.setCreatedUser(null);
        product.setModifiedTime(null);
        product.setModifiedUser(null);
        return product;
    }
}
