package com.ordering.mvc.service.product;

import com.ordering.mvc.exception.ProductNotFoundException;
import com.ordering.mvc.model.product.ProductInfo;
import com.ordering.mvc.process.ProductSearchProcessor;
import com.ordering.mvc.repository.product.ProductRepository;
import com.ordering.mvc.request.product.ProductFilterRequest;
import com.ordering.mvc.response.product.ProductResponse;
import com.ordering.mvc.service.common.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductSearchService implements BaseService<ProductFilterRequest, Page<ProductResponse>> {

    private final ProductRepository productRepository;
    private final ProductSearchProcessor productSearchProcessor;

    @Override
    public Page<ProductResponse> doProcess(ProductFilterRequest req) {

        Sort sort = req.getSortDir().equalsIgnoreCase("desc")
                ? Sort.by(req.getSortBy()).descending()
                : Sort.by(req.getSortBy()).ascending();

        Pageable pageable = PageRequest.of(req.getPage(), req.getSize(), sort);

        Specification<ProductInfo> spec = productSearchProcessor.buildSpecification(req);

        Page<ProductInfo> productPage = productRepository.findAll(spec, pageable);

        if(productPage.isEmpty()){
            throw new ProductNotFoundException();
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


