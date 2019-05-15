package com.adiaz.springmvc.services;

import com.adiaz.springmvc.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> listAll();
    Product getById(Integer id);
    Product saveOrUpdate(Product product);
    void delete(Integer id);
}
