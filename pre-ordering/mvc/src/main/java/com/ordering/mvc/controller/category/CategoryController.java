package com.ordering.mvc.controller.category;

import com.ordering.mvc.request.category.CategoryRequest;
import com.ordering.mvc.response.category.CategoryResponse;
import com.ordering.mvc.response.common.ApiResponse;
import com.ordering.mvc.service.category.CategoryGetAllService;
import com.ordering.mvc.service.category.CategoryGetByIdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryGetAllService categoryGetAllService;
    private final CategoryGetByIdService categoryGetByIdService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<Page<CategoryResponse>>> getAllCategories(@PageableDefault(size = 20, sort = "categoryName") Pageable pageable) {
        Page<CategoryResponse> responses = categoryGetAllService.doProcess(pageable);
        return ResponseEntity.ok(ApiResponse.<Page<CategoryResponse>>builder()
                .status("SUCCESS")
                .message("Fetched all categories successfully")
                .code(200)
                .data(responses)
                .build());
    }

    @PostMapping("/detail")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@RequestBody CategoryRequest request) {
        CategoryResponse response = categoryGetByIdService.doProcess(request);
        return ResponseEntity.ok(ApiResponse.<CategoryResponse>builder()
                .status("SUCCESS")
                .message("Fetched category successfully")
                .code(200)
                .data(response)
                .build());
    }
}



