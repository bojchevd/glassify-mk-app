package com.example.glassify.web;

import com.example.glassify.model.OrderRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/*")
public class OrderController {

    @PostMapping("/")
    public void createOrder(@RequestBody OrderRequest orderRequest) {
        return;
    }
}
