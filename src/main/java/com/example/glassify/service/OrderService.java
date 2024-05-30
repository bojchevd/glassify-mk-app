package com.example.glassify.service;

import com.example.glassify.model.*;
import com.example.glassify.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    public void createOrder(OrderRequest orderRequest) {
        Order order = decomposeOrder(orderRequest);
        saveOrder(order);
    }
    public void saveOrder(Order order) {
        // Perform common business logic (e.g., validation, processing)
        // Save order data to the database via repository
        orderRepository.save(order);
    }

    public Order decomposeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        // TODO set user

        Integer totalOrderSum = 0;

        for (OrderRequestProduct orderRequestProduct : orderRequest.getProductList()) {
            OrderDetails orderDetails = new OrderDetails();

            Product product = productService.getProductByName(orderRequestProduct.getProductType());
            orderDetails.setProduct(product);
            orderDetails.setQuantity(orderRequestProduct.getQuantity());
            orderDetails.setSubtotal(orderDetails.getQuantity() * product.getSalesPrice());

            orderDetails.setSongName(orderRequestProduct.getSongName());
            orderDetails.setArtistName(orderRequestProduct.getArtistName());
            orderDetails.setSongUrl(orderRequestProduct.getSongUrl());
            orderDetails.setCustomDetails(orderRequestProduct.getCustomDetails());
            orderDetails.setFrameColor(orderRequestProduct.getFrameColor());
            // TODO : fix
            orderDetails.setPhotoUrl(orderRequestProduct.getPhotoFile().getOriginalFilename());

            totalOrderSum += orderDetails.getSubtotal();
        }


        order.setTotalAmount(totalOrderSum);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.NEW);

        order.setFullName(orderRequest.getFullName());
        order.setPhoneNumber(order.getPhoneNumber());
        order.setCity(order.getCity());
        order.setAddress(order.getAddress());

        return order;
    }
}
