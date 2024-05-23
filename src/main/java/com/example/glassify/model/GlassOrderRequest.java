package com.example.glassify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlassOrderRequest {
    private String productName;
    private String songName;
    private String artistName;
    private String songUrl;
    private String customDetails;
}
