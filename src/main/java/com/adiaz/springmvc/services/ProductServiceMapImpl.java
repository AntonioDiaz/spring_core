package com.adiaz.springmvc.services;

import com.adiaz.springmvc.domain.Product;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@Profile("map")
public class ProductServiceMapImpl implements ProductService {

    Map<Integer, Product> products;

    public ProductServiceMapImpl() {
        this.products = new HashMap<>();
        this.products.put(1, new Product(1, "Pro. 1", new BigDecimal("12.99"), "http://111"));
        this.products.put(2, new Product(2, "Pro. 2", new BigDecimal("66.99"), "http://222"));
        this.products.put(3, new Product(3, "Pro. 3", new BigDecimal("166.99"), "http://333"));
    }

    @Override
    public List<Product> listAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public Product getById(Integer id) {
        return products.get(id);
    }

    @Override
    public Product saveOrUpdate(Product product) {
        if (product!=null) {
            if (product.getId()==null) {
                product.setId(getNextKey());
            }
            products.put(product.getId(), product);
            return product;
        } else
            throw new RuntimeException("Product can not be null");
    }

    @Override
    public void delete(Integer id) {
        products.remove(id);
    }

    private Integer getNextKey() {
        return Collections.max(products.keySet()) + 1;
    }
}
