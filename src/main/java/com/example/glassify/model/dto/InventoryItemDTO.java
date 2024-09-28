package com.example.glassify.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryItemDTO {
    private String name;
    private Integer stockQuantity;
    private String vendor;
    private String contactPhoneNumber;
    private String contactEmail;
}
