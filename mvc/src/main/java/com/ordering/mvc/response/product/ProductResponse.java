package com.ordering.mvc.response.product;

import com.ordering.mvc.model.product.ProductInfo;
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
public class ProductResponse {
    private UUID productId;
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private String avatarUrl;
    private String quantityPerUnit;
    private int unitsInStock;
    private BigDecimal discount;
    public static ProductResponse fromEntity(ProductInfo entity) {
        return ProductResponse.builder()
                .productId(entity.getId())
                .productName(entity.getProductName())
                .productDescription(entity.getProductDescription())
                .productPrice(entity.getProductPrice())
                .avatarUrl(entity.getAvatarUrl())
                .quantityPerUnit(entity.getQuantityPerUnit())
                .unitsInStock(entity.getUnitsInStock())
                .discount(entity.getDiscount())
                .build();
    }
}
