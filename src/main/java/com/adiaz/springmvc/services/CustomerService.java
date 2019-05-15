package com.adiaz.springmvc.services;

import com.adiaz.springmvc.domain.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> listAll();
    Customer getById(Integer id);
    Customer createOrUpdate(Customer customer);
    void delete(Integer id);
}
