package com.fly.store.mapper;

import com.fly.store.pojo.Product;

import java.util.List;

public interface ProductMapper {
    List<Product> selectHotProduct();
    Product selectById(Integer id);
}
