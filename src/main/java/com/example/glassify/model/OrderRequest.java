package com.example.glassify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String productType;
    private String songName;
    private String artistName;
    private boolean customPhotoUploaded;
}
