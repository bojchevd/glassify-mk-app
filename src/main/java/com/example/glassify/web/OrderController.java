package com.example.glassify.web;

import com.example.glassify.model.CustomPhotoOrderRequest;
import com.example.glassify.model.OrderRequest;
import com.example.glassify.model.PosterOrderRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @PostMapping("/generic")
    public void createGenericOrder(@RequestBody OrderRequest orderRequest) {
        // Handle generic order request (fallback)
        // You can add logic to handle common order attributes
        System.out.println("Received generic order request");
    }

    @PostMapping("/customPhotoOrder")
    public void createCustomPhotoOrder(@RequestBody CustomPhotoOrderRequest orderRequest) {
        // Handle custom photo order request
        // Access additional parameter specific to CustomPhotoOrderRequest
        System.out.println("Received custom photo order request");
        System.out.println("Photo file name: " + orderRequest.getPhotoFile().getOriginalFilename());
    }

    @PostMapping("/posterOrder")
    public void createPosterOrder(@RequestBody PosterOrderRequest orderRequest) {
        // Handle poster order request
        // Access additional parameters specific to PosterOrderRequest
        System.out.println("Received poster order request");
        System.out.println("Has frame: " + orderRequest.isFramed());
        System.out.println("Frame color: " + orderRequest.getFrameColor());
    }
}
