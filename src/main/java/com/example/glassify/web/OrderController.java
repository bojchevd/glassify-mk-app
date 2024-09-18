package com.example.glassify.web;

import com.example.glassify.model.Cart;
import com.example.glassify.model.Order;
import com.example.glassify.model.ShippingInfo;
import com.example.glassify.schedulers.GenerateOrderReport;
import com.example.glassify.service.CartService;
import com.example.glassify.service.OrderService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private GenerateOrderReport generateOrderReport;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PostMapping("/submit/{cartId}")
    public ResponseEntity<?> submitOrder(@PathVariable("cartId") Long cartId, @RequestBody ShippingInfo shippingInfo) {
        try {
            Cart cart = cartService.getCartById(cartId);
            Order order = orderService.createOrder(cart, shippingInfo);

            cartService.clearCart(cartId);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("image") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file selected for upload.");
        }

        try {
            // Log file details
            logger.info("Received file: {}", file.getOriginalFilename());
            logger.info("File size: {} bytes", file.getSize());
            logger.info("File content type: {}", file.getContentType());

            // For now, just return a success message
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (Exception e) {
            logger.error("File upload failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/generate")
    public void generate() throws MessagingException {
        generateOrderReport.generateDailyInvoices();
    }
}
