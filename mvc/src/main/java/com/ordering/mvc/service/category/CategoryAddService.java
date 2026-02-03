package com.ordering.mvc.service.category;

import com.ordering.mvc.model.category.CategoryInfo;
import com.ordering.mvc.repository.category.CategoryRepository;
import com.ordering.mvc.request.category.CategoryAddRequest;
import com.ordering.mvc.response.category.CategoryResponse;
import com.ordering.mvc.service.common.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryAddService implements BaseService<CategoryAddRequest, CategoryResponse> {
    private final CategoryRepository categoryRepository;
    @Override
    public CategoryResponse doProcess(CategoryAddRequest request) {
        if (request.getCategoryName() == null || request.getCategoryName().isEmpty()) {
            throw new IllegalArgumentException("Category name is required");
        }
        CategoryInfo  categoryInfo = new CategoryInfo();
        categoryInfo.setCategoryName(request.getCategoryName());
        categoryInfo.setCategoryDescription(request.getCategoryDescription());
        CategoryInfo saved = categoryRepository.save(categoryInfo);

        CategoryResponse response = new CategoryResponse();

        response.setCategoryName(saved.getCategoryName());
        response.setCategoryDescription(saved.getCategoryDescription());
        return response;
    }
}
