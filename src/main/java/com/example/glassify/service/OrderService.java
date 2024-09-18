package com.example.glassify.service;

import com.example.glassify.model.*;
import com.example.glassify.model.enums.OrderStatus;
import com.example.glassify.repository.OrderRepository;
import com.example.glassify.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EmailService emailService;

    public Order createOrder(Cart cart, ShippingInfo shippingInfo) {
        Order order = new Order();

        int totalPrice = 0;
        for (CartItem cartItem : cart.getItems()) {

            Product product = productRepository.findById(cartItem.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setSubtotal(cartItem.getPrice());
            orderItem.setCustomDetails(cartItem.getCustomDetails());
            orderItem.setPhotoUrl(cartItem.getPhotoUrl());
            orderItem.setFrameColor(cartItem.getFrameColor());
            orderItem.setSongName(cartItem.getSongName());
            orderItem.setArtistName(cartItem.getArtistName());

            orderItem.setOrder(order);

            totalPrice += orderItem.getSubtotal();
            order.getItems().add(orderItem);
        }
        order.setTotalPrice(totalPrice);

        order.setFullName(shippingInfo.getFullName());
        order.setPhoneNumber(shippingInfo.getPhoneNumber());
        order.setAddress(shippingInfo.getAddress());
        order.setCity(shippingInfo.getCity());
        order.setEmail(shippingInfo.getEmail());

        order.setOrderStatus(OrderStatus.NEW);

        String subject = "New Order!";
        String text = "Total: " + order.getTotalPrice() + "\nFull Name: " + shippingInfo.getFullName();
        emailService.sendEmail("glassifymk@gmail.com", subject, text);
        return orderRepository.save(order);
    }

    public List<Order> getAllOrdersBetweenDates(LocalDateTime from, LocalDateTime to) {
        return orderRepository.findAllByCreatedAtBetween(from, to);
    }
}
