package com.ordering.mvc.service.category;

import com.ordering.mvc.exception.CategoryNotFoundException;
import com.ordering.mvc.model.category.CategoryInfo;
import com.ordering.mvc.repository.category.CategoryRepository;
import com.ordering.mvc.request.category.CategoryUpdateRequest;
import com.ordering.mvc.response.category.CategoryResponse;
import com.ordering.mvc.service.common.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryUpdateService implements BaseService<CategoryUpdateRequest, CategoryResponse> {
    private final CategoryRepository categoryRepository;
    @Override
    public CategoryResponse doProcess(CategoryUpdateRequest request) {
        if(request.getCategoryId()==null){
            throw new CategoryNotFoundException();
        }
        CategoryInfo  category = categoryRepository.findById(request.getCategoryId()).orElseThrow( CategoryNotFoundException::new);
        if(category.getCategoryName()!=null){
            category.setCategoryName(request.getCategoryName());
        }
        if(category.getCategoryDescription()!=null){
            category.setCategoryDescription(request.getCategoryDescription());
        }
        CategoryInfo updated =categoryRepository.save(category);
        return CategoryResponse.builder()
                .categoryId(updated.getId())
                .categoryName(updated.getCategoryName())
                .categoryDescription(updated.getCategoryDescription())
                .build();
    }
}
