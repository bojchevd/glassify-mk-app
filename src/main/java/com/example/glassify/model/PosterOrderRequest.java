package com.example.glassify.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PosterOrderRequest extends GlassOrderRequest {
    private boolean framed;
    private String frameColor;
}
