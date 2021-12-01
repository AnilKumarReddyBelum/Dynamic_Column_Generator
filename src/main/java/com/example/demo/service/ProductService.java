package com.example.demo.service;

import com.example.demo.domain.Product;

import java.util.Collection;

public interface ProductService {

    Product create(Product product);

    Product update(Product product);

    Product findById(Long id) throws Exception;

    void deleteById(Long id);

    void delete(Product product);

    Collection<Product> findAll();

}
