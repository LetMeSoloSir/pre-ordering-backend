package com.ordering.mvc.request.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRemoveRequest {
    private String userId;
    private UUID productId;
}

