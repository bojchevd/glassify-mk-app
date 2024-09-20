package com.example.glassify.web;

import com.example.glassify.model.Cart;
import com.example.glassify.model.Order;
import com.example.glassify.model.ShippingInfo;
import com.example.glassify.model.dto.OrderListWrapperDTO;
import com.example.glassify.schedulers.GenerateOrderReport;
import com.example.glassify.service.CartService;
import com.example.glassify.service.OrderService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/get")
    public ResponseEntity<OrderListWrapperDTO> getOrders(@RequestParam(defaultValue = "0") int offset,
                                                         @RequestParam(defaultValue = "10") int limit) {
        Pageable pageable = PageRequest.of(offset/limit, limit);
        Page<Order> orderPage = orderService.findOrders(pageable);

        OrderListWrapperDTO wrapperDTO = new OrderListWrapperDTO();
        wrapperDTO.setOrders(orderPage.getContent());
        wrapperDTO.setTotalCount(orderPage.getTotalElements());

        return ResponseEntity.ok(wrapperDTO);
    }

    @DeleteMapping("/{orderId}/delete")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) throws Exception {
        orderService.deleteOrderById(orderId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Long orderId,
                                                  @RequestBody Map<String, String> statusRequest) throws Exception {
        String newStatus = statusRequest.get("status");
        orderService.updateOrderStatus(orderId, newStatus);
        return ResponseEntity.ok().build();
    }

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
            // Define the directory to store the files
            String uploadDir = "uploads/";

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
