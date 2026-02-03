package com.ordering.mvc.request.product;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductAddRequest {
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private String productImageUrl;
    private UUID categoryId;
    private String quantityPerUnit;
    private BigDecimal discount;
    private int unitsInStock;


}
