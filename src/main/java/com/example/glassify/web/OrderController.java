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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "No file selected for upload.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        try {
            // Log file details
            logger.info("Received file: {}", file.getOriginalFilename());
            logger.info("File size: {} bytes", file.getSize());
            logger.info("File content type: {}", file.getContentType());

            // Define the directory to store the files
            String uploadDir = "uploads/";

            // Ensure the directory exists
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            Path filePath = Paths.get(uploadDir + file.getOriginalFilename() + ".jpg");
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Map<String, String> response = new HashMap<>();
            response.put("fileUrl", filePath.toString());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("File upload failed", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "File upload failed");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/generate")
    public void generate() throws MessagingException {
        generateOrderReport.generateDailyInvoices();
    }
}
