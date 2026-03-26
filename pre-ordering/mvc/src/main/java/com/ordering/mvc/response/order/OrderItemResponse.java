package com.ordering.mvc.response.order;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderItemResponse {
    private UUID productId;
    private String productName;
    private int unitPrice;
    private int quantity;
    private int totalPrice;
}
