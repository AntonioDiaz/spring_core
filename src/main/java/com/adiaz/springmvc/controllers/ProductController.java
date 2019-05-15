package com.adiaz.springmvc.controllers;

import com.adiaz.springmvc.domain.Product;
import com.adiaz.springmvc.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("list", productService.listAll());
        return "product_list";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.getById(id));
        return "product_view";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        return "product_new";
    }

    @PostMapping("/doCreate")
    public String doCreate(Product product) {
        Product savedProduct = productService.saveOrUpdate(product);
        return "redirect:/product/show/" + savedProduct.getId();
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.getById(id));
        return "product_update";
    }

    @GetMapping("/doDelete/{id}")
    public String doDelete(@PathVariable Integer id) {
        productService.delete(id);
        return "redirect:/product/list";
    }



}
