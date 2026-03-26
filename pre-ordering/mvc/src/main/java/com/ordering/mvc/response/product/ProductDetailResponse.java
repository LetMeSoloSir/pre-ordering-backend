package com.ordering.mvc.response.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDetailResponse {
    private UUID productId;
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private String avatarUrl;
    private String quantityPerUnit;
    private int unitsInStock;
    private int unitsOnOrder;
    private int reorderLevel;
    private BigDecimal discount;
    private String status;
}
