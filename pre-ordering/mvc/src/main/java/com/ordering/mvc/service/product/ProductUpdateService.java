package com.ordering.mvc.service.product;

import com.ordering.mvc.exception.InvalidSetStatusForProductException;
import com.ordering.mvc.exception.ProductIsDeleteException;
import com.ordering.mvc.exception.ProductNotFoundException;
import com.ordering.mvc.model.product.ProductInfo;
import com.ordering.mvc.model.product.ProductStatus;
import com.ordering.mvc.repository.product.ProductRepository;
import com.ordering.mvc.request.product.ProductUpdateRequest;
import com.ordering.mvc.response.product.ProductDetailResponse;
import com.ordering.mvc.response.product.ProductResponse;
import com.ordering.mvc.service.common.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductUpdateService implements BaseService<ProductUpdateRequest, ProductDetailResponse> {

    private final ProductRepository productRepository;

    @Override
    public ProductDetailResponse doProcess(ProductUpdateRequest request) {

        if (request.getProductId() == null) {
            throw new ProductNotFoundException();
        }

        ProductInfo product = productRepository.findById(request.getProductId())
                .orElseThrow(ProductNotFoundException::new);

        if (product.getStatus() != ProductStatus.PUBLIC && product.getStatus() != ProductStatus.UNPUBLIC) {
            throw new InvalidSetStatusForProductException();
        }
        if(request.getStatus() != null) product.setStatus(request.getStatus());
        if(product.getIsDeleted() ==true){
            throw new ProductIsDeleteException();
        }

        if (request.getProductName() != null) product.setProductName(request.getProductName());
        if (request.getProductDescription() != null) product.setProductDescription(request.getProductDescription());
        if (request.getProductPrice() != null && request.getProductPrice().compareTo(BigDecimal.ZERO) > 0) {
            product.setProductPrice(request.getProductPrice());
        }
        if (request.getAvatarUrl() != null) product.setAvatarUrl(request.getAvatarUrl());
        if (request.getQuantityPerUnit() != null) product.setQuantityPerUnit(request.getQuantityPerUnit());
        product.setUnitsInStock(request.getUnitsInStock());
        if (request.getDiscount() != null && request.getDiscount().compareTo(BigDecimal.ZERO) >= 0) {
            product.setDiscount(request.getDiscount());
        }

        ProductInfo updated = productRepository.save(product);

        return ProductDetailResponse.builder()
                .productId(updated.getId())
                .productName(updated.getProductName())
                .productDescription(updated.getProductDescription())
                .productPrice(updated.getProductPrice())
                .avatarUrl(updated.getAvatarUrl())
                .quantityPerUnit(updated.getQuantityPerUnit())
                .unitsInStock(updated.getUnitsInStock())
                .discount(updated.getDiscount())
                .build();
    }
}

