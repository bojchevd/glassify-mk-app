package com.example.glassify.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomPhotoGlassOrderRequest extends GlassOrderRequest {
    private MultipartFile photoFile;
}
