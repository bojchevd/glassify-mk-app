package com.example.glassify.service;

import com.example.glassify.model.*;
import com.example.glassify.model.enums.OrderStatus;
import com.example.glassify.repository.OrderRepository;
import com.example.glassify.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public Order createOrder(Cart cart) {
        Order order = new Order();

        int totalPrice = 0;
        for (CartItem cartItem : cart.getItems()) {

            Product product = productRepository.findById(cartItem.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setCustomDetails(cartItem.getCustomDetails());
            orderItem.setPhotoUrl(cartItem.getPhotoUrl());
            orderItem.setFrameColor(cartItem.getFrameColor());
            orderItem.setSongName(cartItem.getSongName());
            orderItem.setArtistName(cartItem.getArtistName());

            int itemPrice = product.getBasePrice() * cartItem.getQuantity();
            totalPrice += itemPrice;

            order.getItems().add(orderItem);
        }

        order.setTotalPrice(totalPrice);
        order.setOrderStatus(OrderStatus.NEW);

        return orderRepository.save(order);
    }
}
