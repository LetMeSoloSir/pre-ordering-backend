package com.ordering.mvc.controller.category;

import com.ordering.mvc.request.category.CategoryAddRequest;
import com.ordering.mvc.request.category.CategoryRequest;
import com.ordering.mvc.request.category.CategoryUpdateRequest;

import com.ordering.mvc.response.category.CategoryResponse;
import com.ordering.mvc.response.common.ApiResponse;
import com.ordering.mvc.service.category.CategoryAddService;
import com.ordering.mvc.service.category.CategoryDeleteService;
import com.ordering.mvc.service.category.CategoryUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/category")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
public class AdminCategoryController {
    private final CategoryAddService categoryAddService;
    private final CategoryDeleteService categoryDeleteService;
    private final CategoryUpdateService categoryUpdateService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<CategoryResponse>> addProduct(@RequestBody CategoryAddRequest request) {
        CategoryResponse response = categoryAddService.doProcess(request);

        return ResponseEntity.ok(ApiResponse.<CategoryResponse>builder()
                .status("SUCCESS")
                .code(200)
                .message("Category created successfully")
                .data(response)
                .build());
    }
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> delete(@RequestBody CategoryRequest request) {
        categoryDeleteService.doProcess(request);

        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status("SUCCESS")
                .code(200)
                .message("Category delete successfully")
                .data(null)
                .build());
    }
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateProduct(@RequestBody CategoryUpdateRequest request) {
        CategoryResponse response = categoryUpdateService.doProcess(request);
        return ResponseEntity.ok(ApiResponse.<CategoryResponse>builder()
                .status("SUCCESS")
                .code(200)
                .message("Category Update successfully")
                .data(response)
                .build());
    }

}
