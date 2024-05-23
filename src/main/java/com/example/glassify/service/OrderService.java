package com.example.glassify.service;

import com.example.glassify.model.CustomPhotoGlassOrderRequest;
import com.example.glassify.model.GlassOrderRequest;
import com.example.glassify.model.PosterOrderRequest;
import com.example.glassify.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    public void createGenericOrder(GlassOrderRequest glassOrderRequest) {
        // Perform common business logic (e.g., validation, processing)
        // Save order data to the database via repository
        orderRepository.save(glassOrderRequest);
    }

    public void createCustomPhotoOrder(CustomPhotoGlassOrderRequest orderRequest) {
        // Perform custom logic specific to custom photo orders
        // Save order data to the database via repository
        orderRepository.save(orderRequest);
    }

    public void createPosterOrder(PosterOrderRequest orderRequest) {
        // Perform custom logic specific to poster orders
        // Save order data to the database via repository
        orderRepository.save(orderRequest);
    }

    // Add more methods for other specific order types as needed
}
