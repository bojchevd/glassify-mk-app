package com.example.glassify.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDTO {
    private String productName;
    private String songName;
    private String artistName;
    private String customDetails;
    private String albumName;
    private String frameColor;
    private String photoUrl;
    private Integer quantity;
    private String fullName;
    private String deliveryAddress;
    private String city;
    private String phoneNumber;
}
