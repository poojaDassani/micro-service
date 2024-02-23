package com.whizzarc.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemsResponse {
    private Long id;
    private String skuCode;
    private Double price;
    private Double quantity;
}

