package com.fly.store.service;

import com.fly.store.pojo.Product;

import java.util.List;

public interface ProductService {
    List<Product> queryHotProduct();
    Product queryById(Integer id);
}
