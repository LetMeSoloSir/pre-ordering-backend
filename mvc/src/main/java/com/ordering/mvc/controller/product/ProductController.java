package com.ordering.mvc.controller.product;

import com.ordering.mvc.request.product.ProductDetailRequest;
import com.ordering.mvc.request.product.ProductFilterRequest;
import com.ordering.mvc.request.product.ProductListsByCategoryRequest;
import com.ordering.mvc.response.common.ApiResponse;
import com.ordering.mvc.response.product.ProductDetailResponse;
import com.ordering.mvc.response.product.ProductResponse;
import com.ordering.mvc.service.product.ProductRecentGetService;
import com.ordering.mvc.service.product.ProductRecentViewService;
import com.ordering.mvc.service.product.ProductDetailService;
import com.ordering.mvc.service.product.ProductListsByCategoryService;
import com.ordering.mvc.service.product.ProductListsGetAllService;
import com.ordering.mvc.service.product.ProductSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductListsByCategoryService productListsByCategoryService;
    private final ProductListsGetAllService productListsGetAllService;
    private final ProductDetailService productDetailService;
    private final ProductSearchService productSearchService;
    private final ProductRecentGetService productRecentGetService;
    private final ProductRecentViewService productRecentViewService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> getAllForUser(@PageableDefault(size = 20, sort = "productName") Pageable pageable) {
        Page<ProductResponse> productPage = productListsGetAllService.doProcess(pageable);
        return ResponseEntity.ok(ApiResponse.<Page<ProductResponse>>builder()
                .status("SUCCESS")
                .message("Fetched products successfully")
                .code(200)
                .data(productPage)
                .build());
    }

    @PostMapping("/detail")
    public ResponseEntity<ApiResponse<ProductDetailResponse>> getProductById(@RequestBody ProductDetailRequest request,@AuthenticationPrincipal Jwt jwt){
        ProductDetailResponse response = productDetailService.doProcess(request);
        if(jwt != null){
            String userId = jwt.getSubject();
            productRecentViewService.add(userId, request.getProductId());
        }
        return ResponseEntity.ok(ApiResponse.<ProductDetailResponse>builder()
                .status("SUCCESS")
                .message("Fetched product successfully")
                .code(200)
                .data(response)
                .build());
    }

    @PostMapping("/filter")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> filter( @RequestBody ProductFilterRequest request) {
        Page<ProductResponse> response = productSearchService.doProcess(request);
        return ResponseEntity.ok(ApiResponse.<Page<ProductResponse>>builder()
                .status("SUCCESS")
                .message("Filtered products successfully")
                .code(200)
                .data(response)
                .build());
    }

    @PostMapping("/category/list")
    public ResponseEntity<ApiResponse<Page<ProductResponse>>> getProductPageByCategoryId(
            @RequestBody ProductListsByCategoryRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "productName") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction) {
        request.setPage(page);
        request.setSize(size);
        request.setSortBy(sortBy);
        request.setDirection(direction);
        Page<ProductResponse>  productPage = productListsByCategoryService.doProcess(request);
        return ResponseEntity.ok(ApiResponse.<Page<ProductResponse>>builder()
                .status("SUCCESS")
                .message("Fetched products successfully")
                .code(200)
                .data(productPage)
                .build());


    }
    @GetMapping("/recent-viewed")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> recentViewed(
            @AuthenticationPrincipal Jwt jwt
    ) {
        String userId = jwt.getSubject();
        List<ProductResponse> productPage= productRecentGetService.doProcess(userId);
        return ResponseEntity.ok(ApiResponse.<List<ProductResponse>>builder()
                .status("SUCCESS")
                .message("Fetched products successfully")
                .code(200)
                .data(productPage)
                .build());
    }

}
