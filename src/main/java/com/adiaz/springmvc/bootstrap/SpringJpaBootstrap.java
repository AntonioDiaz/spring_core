package com.adiaz.springmvc.bootstrap;

import com.adiaz.springmvc.domain.Address;
import com.adiaz.springmvc.domain.Customer;
import com.adiaz.springmvc.domain.Product;
import com.adiaz.springmvc.domain.User;
import com.adiaz.springmvc.domain.security.Role;
import com.adiaz.springmvc.services.CustomerService;
import com.adiaz.springmvc.services.ProductService;
import com.adiaz.springmvc.services.RoleService;
import com.adiaz.springmvc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Profile("jpadao")
public class SpringJpaBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadProducts();
        loadCustomer();
        loadRoles();
        assignUsersToDefaultRole();

    }

    private void loadCustomer() {
        Customer customer = new Customer();
        customer = new Customer(1, "Pepito", "Grillo", "pepito@gmail.com",
                "666445566", "calle 01", "Portal 2", "leganes",
                "Madrid", "28914");
        customerService.createOrUpdate(customer);

        User user3 = new User();
        user3.setUsername("saxe");
        user3.setPassword("password");
        Customer customer3 = new Customer();
        customer3.setFirstName("Sam");
        customer3.setBillingAddress(new Address());
        customer3.getBillingAddress().setAddressLine1("1 Little Cuba Road");
        customer3.getBillingAddress().setCity("Miami");
        customer3.getBillingAddress().setState("Florida");
        customer3.getBillingAddress().setZipCode("33101");

        user3.setCustomer(customer3);
        userService.createOrUpdate(user3);

    }

    private void loadProducts() {
        productService.saveOrUpdate(new Product(1, "Pro. 1", new BigDecimal("12.99"), "http://111"));
        productService.saveOrUpdate(new Product(2, "Pro. 2", new BigDecimal("66.99"), "http://222"));
        productService.saveOrUpdate(new Product(3, "Pro. 3", new BigDecimal("166.99"), "http://333"));
    }

    private void loadRoles() {
        Role role = new Role();
        role.setRole("CUSTOMER");
        roleService.createOrUpdate(role);
    }

    private void assignUsersToDefaultRole() {
        List<Role> roles = roleService.listAll();
        List<User> users = userService.listAll();
        roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("CUSTOMER")) {
                users.forEach(user ->{
                    user.addRole(role);
                    userService.createOrUpdate(user);
                });
            }
        });
    }

}
