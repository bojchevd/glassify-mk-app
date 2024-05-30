package com.example.glassify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private List<OrderRequestProduct> productList;
    private String fullName;
    private String phoneNumber;
    private String city;
    private String address;
}
