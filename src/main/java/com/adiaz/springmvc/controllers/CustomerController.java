package com.adiaz.springmvc.controllers;

import com.adiaz.springmvc.domain.Customer;
import com.adiaz.springmvc.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("list", customerService.listAll());
        return "customer_list";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        model.addAttribute("customer", customerService.getById(id));
        return "customer_view";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer_new";
    }

    @PostMapping("/doCreate")
    public String doCreate(Customer customer) {
        Customer savedCustomer = customerService.createOrUpdate(customer);
        return "redirect:/customer/" + savedCustomer.getId();
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Integer id, Model model) {
        model.addAttribute("customer", customerService.getById(id));
        return "customer_update";
    }

    @PostMapping("/doUpdate")
    public String doUpdate(Customer customer) {
        Customer savedCustomer = customerService.createOrUpdate(customer);
        return "redirect:/customer/show/" + savedCustomer.getId();
    }

    @GetMapping("/doDelete/{id}")
    public String doDelete(@PathVariable Integer id) {
        customerService.delete(id);
        return "redirect:/customer/list";
    }

}
