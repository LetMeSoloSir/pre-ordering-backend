package com.ordering.mvc.service.product;

import com.ordering.mvc.exception.ProductNotFoundException;
import com.ordering.mvc.model.product.ProductInfo;
import com.ordering.mvc.model.product.ProductStatus;
import com.ordering.mvc.repository.product.ProductRepository;
import com.ordering.mvc.request.product.ProductDetailRequest;
import com.ordering.mvc.response.product.ProductDetailResponse;
import com.ordering.mvc.response.product.ProductResponse;
import com.ordering.mvc.service.common.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductDetailService implements BaseService<ProductDetailRequest, ProductDetailResponse> {
    private final ProductRepository productRepository;

    @Override
    public ProductDetailResponse doProcess(ProductDetailRequest request) {
        ProductInfo productInfo = productRepository.findById(request.getProductId())
                .orElseThrow(ProductNotFoundException::new);
        if (productInfo.getStatus() != ProductStatus.PUBLIC) {
            throw new ProductNotFoundException();
        }

        return ProductDetailResponse.builder()
                .productId(productInfo.getId())
                .productName(productInfo.getProductName())
                .productDescription(productInfo.getProductDescription())
                .productPrice(productInfo.getProductPrice())
                .avatarUrl(productInfo.getAvatarUrl())
                .quantityPerUnit(productInfo.getQuantityPerUnit())
                .unitsInStock(productInfo.getUnitsInStock())
                .reorderLevel(productInfo.getReorderLevel())
                .discount(productInfo.getDiscount())
                .status(productInfo.getStatus() != null ? productInfo.getStatus().name() : null)
                .build();
    }
}
