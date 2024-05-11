package com.example.glassify.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomPhotoOrderRequest extends OrderRequest {
    private MultipartFile photoFile;
}
