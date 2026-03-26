package com.ordering.mvc.service.category;

import com.ordering.mvc.model.category.CategoryInfo;
import com.ordering.mvc.repository.category.CategoryRepository;
import com.ordering.mvc.response.category.CategoryResponse;
import com.ordering.mvc.service.common.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CategoryGetAllService implements BaseService<Pageable, Page<CategoryResponse>> {
    private final CategoryRepository categoryRepository;

    @Override
    public Page<CategoryResponse> doProcess(Pageable pageable) {
        Page<CategoryInfo> categoryPage   = categoryRepository.findAll(pageable);
        return categoryPage.map(c -> new CategoryResponse(
                c.getId(),
                c.getCategoryName(),
                c.getCategoryDescription()
        ));
    }
}
