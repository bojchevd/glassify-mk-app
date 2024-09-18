package com.example.glassify.service;

import com.example.glassify.model.*;
import com.example.glassify.model.enums.OrderStatus;
import com.example.glassify.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EmailService emailService;

    public Page<Order> findOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Order createOrder(Cart cart, ShippingInfo shippingInfo) {
        Order order = new Order();

        int totalPrice = 0;
        for (CartItem cartItem : cart.getItems()) {

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
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
        String confirmationMessage = emailService.generateOrderConfirmationMessage(order);
        emailService.orderNotification("glassifymk@gmail.com", subject, text);
        emailService.orderConfirmation("glassifymk@gmail.com", order.getEmail(), confirmationMessage);
        return orderRepository.save(order);
    }

    public List<Order> getAllOrdersBetweenDates(LocalDateTime from, LocalDateTime to) {
        return orderRepository.findAllByCreatedAtBetween(from, to);
    }

    public void deleteOrderById(Long orderId) throws Exception {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
        } else {
            throw new Exception("Order not found with ID: " + orderId);
        }
    }

    public void updateOrderStatus(Long orderId, String newStatus) throws Exception {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("Order not found with ID: " + orderId));

        try {
            if (newStatus == "SHIPPED") {
                // TODO : Custom logic ex. notification
            }
            order.setOrderStatus(OrderStatus.valueOf(newStatus.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new Exception("Invalid order status: " + newStatus);
        }

        // Save the updated order
        orderRepository.save(order);
    }
}
