package com.example.glassify.service;

import com.example.glassify.model.Product;
import com.example.glassify.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts(){
        return productRepository.getAllProducts();
    }

    public Product getProductById(Long id) {
        return productRepository.getProductById(id);
    }
}
