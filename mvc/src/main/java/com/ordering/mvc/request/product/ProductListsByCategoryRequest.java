package com.ordering.mvc.request.product;

import com.ordering.mvc.request.common.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductListsByCategoryRequest extends BaseRequest {
    private UUID categoryId;
    private String sortBy = "productName";
    private Sort.Direction direction = Sort.Direction.ASC;

    public Pageable toPageable() {
        return PageRequest.of(getPage(), getSize(), Sort.by(direction, sortBy));
    }
}
