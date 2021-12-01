package com.example.demo.controller;

import com.example.demo.domain.Product;
import com.example.demo.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return productService.create(product);
    }

    @PutMapping
    public Product update(@RequestBody Product product) {
        return productService.update(product);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable("id") Long id) {
        productService.deleteById(id);
    }

    @DeleteMapping
    public void deleteById(@RequestBody Product product) {
        productService.delete(product);
    }

    @GetMapping("{id}")
    public Product findById(@PathVariable("id") Long id) throws Exception {
        return productService.findById(id);
    }

    @GetMapping
    public Collection<Product> findAll() {
        return productService.findAll();
    }

}
