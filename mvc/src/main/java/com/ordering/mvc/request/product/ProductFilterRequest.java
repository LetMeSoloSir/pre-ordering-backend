package com.ordering.mvc.request.product;

import com.ordering.mvc.model.product.ProductStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductFilterRequest {
    private String keyword;
    private UUID categoryId;
    private Double minPrice;
    private Double maxPrice;
    private ProductStatus status;

    private String sortBy = "productName";
    private String sortDir = "asc";

    private int page = 0;
    private int size = 10;
}
