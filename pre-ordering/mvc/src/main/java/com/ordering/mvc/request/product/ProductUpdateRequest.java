package com.ordering.mvc.request.product;

import com.ordering.mvc.model.product.ProductStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductUpdateRequest {
    private UUID productId;
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private ProductStatus status;
    private String avatarUrl;
    private String quantityPerUnit;
    private BigDecimal discount;
    private int unitsInStock;
}
