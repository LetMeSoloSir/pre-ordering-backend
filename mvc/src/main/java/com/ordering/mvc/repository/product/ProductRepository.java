package com.ordering.mvc.repository.product;

import com.ordering.mvc.model.product.ProductInfo;
import com.ordering.mvc.model.product.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductInfo, UUID>, JpaSpecificationExecutor<ProductInfo> {
    Page<ProductInfo> findByCategoryIdAndStatus(UUID categoryId, ProductStatus status, Pageable pageable);


    Page<ProductInfo> findByCategoryId(UUID categoryId,Pageable pageable);

    Page<ProductInfo> findByStatus(ProductStatus status, Pageable pageable);

}
