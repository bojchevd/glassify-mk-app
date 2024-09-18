package com.example.glassify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingInfo {
    private String fullName;
    private String phoneNumber;
    private String address;
    private String city;
    private String email;
}
