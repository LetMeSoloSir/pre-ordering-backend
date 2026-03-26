package com.ordering.mvc.service.product;

import com.ordering.mvc.exception.CategoryNotFoundException;
import com.ordering.mvc.model.category.CategoryInfo;
import com.ordering.mvc.model.product.ProductInfo;
import com.ordering.mvc.model.product.ProductStatus;
import com.ordering.mvc.repository.category.CategoryRepository;
import com.ordering.mvc.repository.product.ProductRepository;
import com.ordering.mvc.request.product.ProductAddRequest;
import com.ordering.mvc.response.product.ProductDetailResponse;
import com.ordering.mvc.response.product.ProductResponse;
import com.ordering.mvc.service.common.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductAddService implements BaseService<ProductAddRequest, ProductDetailResponse> {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductDetailResponse doProcess(ProductAddRequest request) {

        if (request.getProductName() == null || request.getProductName().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }

        if (request.getProductPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be > 0");
        }

        ProductInfo entity = new ProductInfo();
        entity.setProductName(request.getProductName());
        entity.setProductPrice(request.getProductPrice());
        entity.setProductDescription(request.getProductDescription());
        entity.setStatus(ProductStatus.UNPUBLIC);
        entity.setAvatarUrl(request.getProductImageUrl());
        entity.setQuantityPerUnit(request.getQuantityPerUnit());
        entity.setUnitsInStock(request.getUnitsInStock());
        entity.setDiscount(request.getDiscount() != null ? request.getDiscount() : BigDecimal.ZERO);
        entity.setReorderLevel(0);
        entity.setUnitsOnOrder(0);
        if (request.getCategoryId() != null) {
            CategoryInfo category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(CategoryNotFoundException::new);
            entity.setCategory(category);
        }
        ProductInfo saved = productRepository.save(entity);

        ProductDetailResponse response = new ProductDetailResponse();
        response.setProductName(saved.getProductName());
        response.setProductDescription(saved.getProductDescription());
        response.setProductPrice(saved.getProductPrice());
        response.setAvatarUrl(saved.getAvatarUrl());
        response.setQuantityPerUnit(saved.getQuantityPerUnit());
        response.setUnitsOnOrder(saved.getUnitsOnOrder());
        response.setUnitsInStock(saved.getUnitsInStock());
        response.setReorderLevel(saved.getReorderLevel());
        response.setDiscount(saved.getDiscount());
        response.setStatus(saved.getStatus().getDescription());


        return response;
    }
}
