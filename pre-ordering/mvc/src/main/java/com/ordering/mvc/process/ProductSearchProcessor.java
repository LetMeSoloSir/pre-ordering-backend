package com.ordering.mvc.process;

import com.ordering.mvc.model.product.ProductInfo;
import com.ordering.mvc.model.product.ProductStatus;
import com.ordering.mvc.request.product.ProductFilterRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductSearchProcessor {

    public Specification<ProductInfo> buildSpecification(ProductFilterRequest req) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();


            if (req.getKeyword() != null && !req.getKeyword().isEmpty()) {
                predicates.add(cb.like(
                        cb.lower(root.get("productName")),
                        "%" + req.getKeyword().toLowerCase() + "%"
                ));
            }

            if (req.getCategoryId() != null) {
                predicates.add(cb.equal(root.get("category").get("id"), req.getCategoryId()));
            }

            if (req.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("productPrice"), req.getMinPrice()));
            }

            if (req.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("productPrice"), req.getMaxPrice()));
            }
            predicates.add(
                    cb.equal(root.get("status"), ProductStatus.PUBLIC));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
