package com.example.glassify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestProduct {
    private String productType;
    private String songName;
    private String artistName;
    private String songUrl;
    private String customDetails;
    private String frameColor;
    private MultipartFile photoFile;
    private Integer quantity;
}
