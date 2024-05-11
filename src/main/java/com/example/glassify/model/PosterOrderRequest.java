package com.example.glassify.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PosterOrderRequest extends OrderRequest{
    private boolean framed;
    private String frameColor;
}
