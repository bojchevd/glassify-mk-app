package com.example.glassify.repository;

import com.example.glassify.model.Product;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductRepository {

    public List<Product> getAllProducts(){
        return new ArrayList<>();
    }

    public Product getProductById(Long id) {
        return new Product();
    }
}
