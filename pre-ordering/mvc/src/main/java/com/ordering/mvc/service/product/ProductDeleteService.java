package com.ordering.mvc.service.product;

import com.ordering.mvc.exception.ProductNotFoundException;
import com.ordering.mvc.model.product.ProductStatus;
import com.ordering.mvc.repository.product.ProductRepository;
import com.ordering.mvc.request.product.ProductDetailRequest;
import com.ordering.mvc.service.common.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductDeleteService implements BaseService<ProductDetailRequest,Void> {
    private final ProductRepository productRepository;
    @Override
    public Void doProcess(ProductDetailRequest request) {

        var product = productRepository.findById(request.getProductId())
                .orElseThrow(ProductNotFoundException::new);

        product.setStatus(ProductStatus.DELETED);
        product.setIsDeleted(true);

        productRepository.save(product);

        return null;
    }
}
