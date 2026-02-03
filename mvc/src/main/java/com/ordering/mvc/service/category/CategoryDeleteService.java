package com.ordering.mvc.service.category;

import com.ordering.mvc.repository.category.CategoryRepository;
import com.ordering.mvc.request.category.CategoryRequest;
import com.ordering.mvc.service.common.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryDeleteService implements BaseService<CategoryRequest,Void> {
    private final CategoryRepository categoryRepository;
    @Override
    public Void doProcess(CategoryRequest request) {
    categoryRepository.deleteById(request.getCategoryId());
        return null;
    }
}
