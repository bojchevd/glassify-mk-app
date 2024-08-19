package com.example.glassify.web;

import com.example.glassify.model.Cart;
import com.example.glassify.model.Order;
import com.example.glassify.service.CartService;
import com.example.glassify.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @PostMapping("/submit/{cartId}")
    public ResponseEntity<?> submitOrder(@PathVariable("cartId") Long cartId) {
        try {
            Cart cart = cartService.getCartById(cartId);

            Order order = orderService.createOrder(cart);

            cartService.clearCart(cartId);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
}
