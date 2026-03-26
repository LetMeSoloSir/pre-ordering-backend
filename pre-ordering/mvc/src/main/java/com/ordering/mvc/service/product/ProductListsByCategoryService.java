package com.ordering.mvc.service.product;

import com.ordering.mvc.exception.CategoryIsEmptyException;
import com.ordering.mvc.exception.ProductNotFoundException;
import com.ordering.mvc.model.product.ProductInfo;
import com.ordering.mvc.model.product.ProductStatus;
import com.ordering.mvc.repository.product.ProductRepository;
import com.ordering.mvc.request.product.ProductListsByCategoryRequest;
import com.ordering.mvc.response.product.ProductResponse;
import com.ordering.mvc.service.common.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductListsByCategoryService implements BaseService<ProductListsByCategoryRequest, Page<ProductResponse>> {
    private final ProductRepository productRepository;

    @Override
    public Page<ProductResponse> doProcess(ProductListsByCategoryRequest request) {
        Page<ProductInfo> productPage = productRepository.findByCategoryIdAndStatus(request.getCategoryId(),ProductStatus.PUBLIC,request.toPageable());

        if (productPage.isEmpty()) {
            throw new CategoryIsEmptyException();
        }
        return productPage.map(productInfo -> ProductResponse.builder()
                .productId(productInfo.getId())
                .productName(productInfo.getProductName())
                .productDescription(productInfo.getProductDescription())
                .productPrice(productInfo.getProductPrice())
                .avatarUrl(productInfo.getAvatarUrl())
                .quantityPerUnit(productInfo.getQuantityPerUnit())
                .unitsInStock(productInfo.getUnitsInStock())
                .discount(productInfo.getDiscount())
                .build());
    }
}
