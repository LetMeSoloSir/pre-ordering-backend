package com.ordering.mvc.service.category;

import com.ordering.mvc.exception.CategoryNotFoundException;
import com.ordering.mvc.model.category.CategoryInfo;
import com.ordering.mvc.repository.category.CategoryRepository;
import com.ordering.mvc.request.category.CategoryRequest;
import com.ordering.mvc.response.category.CategoryResponse;
import com.ordering.mvc.service.common.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CategoryGetByIdService implements BaseService<CategoryRequest, CategoryResponse> {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse doProcess(CategoryRequest request) {
        CategoryInfo category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        return new CategoryResponse(
                category.getId(),
                category.getCategoryName(),
                category.getCategoryDescription()
        );
    }
}

