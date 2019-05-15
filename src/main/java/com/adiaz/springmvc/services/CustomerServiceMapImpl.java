package com.adiaz.springmvc.services;

import com.adiaz.springmvc.domain.Customer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Profile("map")
public class CustomerServiceMapImpl implements CustomerService {

    Map<Integer, Customer> customers;

    public CustomerServiceMapImpl(){
        customers = new HashMap<>();
        customers.put(1, new Customer(1, "Pepito", "Grillo", "pepito@gmail.com",
                "666445566", "calle 01", "Portal 2", "leganes",
                "Madrid", "28914"));
        customers.put(2, new Customer(2, "Margarita", "Griso", "griso.margarita@gmail.com",
                "666445566", "calle 01", "Portal 2", "leganes",
                "Madrid", "28914"));
    }

    @Override
    public List<Customer> listAll() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public Customer getById(Integer id) {
        return customers.get(id);
    }

    @Override
    public Customer createOrUpdate(Customer customer) {
        if (customer!=null) {
            if (customer.getId()==null) {
                customer.setId(getNextKey());
            }
            customers.put(customer.getId(), customer);
            return customer;
        } else
            throw new RuntimeException("Product can not be null");
    }

    private Integer getNextKey() {
        return Collections.max(customers.keySet()) + 1;
    }

    @Override
    public void delete(Integer id) {
        customers.remove(id);
    }
}
