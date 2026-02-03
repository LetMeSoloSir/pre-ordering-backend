package com.ordering.mvc.request.cart;

import lombok.Data;

import java.util.UUID;

@Data
public class CartUpdateRequest {
    private UUID userId;
    private UUID productId;
    private Integer quantity;
}

