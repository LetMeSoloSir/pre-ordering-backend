package com.ordering.mvc.request.cart;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CartAddRequest {
    private UUID userId;
    private UUID productId;
    private String productName;
    private BigDecimal unitPrice;
    private Integer quantity;
    private String imageUrl;
}

