package com.example.glassify.model.dto;

import com.example.glassify.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderListWrapperDTO {
    private List<Order> orders;
    private long totalCount;
}
