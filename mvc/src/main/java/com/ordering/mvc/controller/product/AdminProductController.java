package com.ordering.mvc.controller.product;

import com.ordering.mvc.request.product.ProductAddRequest;
import com.ordering.mvc.request.product.ProductDetailRequest;
import com.ordering.mvc.request.product.ProductUpdateRequest;
import com.ordering.mvc.response.common.ApiResponse;
import com.ordering.mvc.response.product.ProductDetailResponse;
import com.ordering.mvc.response.product.ProductResponse;
import com.ordering.mvc.service.product.ProductAddService;
import com.ordering.mvc.service.product.ProductDeleteService;
import com.ordering.mvc.service.product.ProductUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin/product")
@RequiredArgsConstructor
@Slf4j
public class AdminProductController {
    private final ProductAddService productAddService;
    private final ProductUpdateService productUpdateService;
    private final ProductDeleteService productDeleteService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<ProductDetailResponse>> addProduct(@RequestBody ProductAddRequest request) {
        ProductDetailResponse response = productAddService.doProcess(request);

        return ResponseEntity.ok(ApiResponse.<ProductDetailResponse>builder()
                .status("SUCCESS")
                .code(200)
                .message("Product created successfully")
                .data(response)
                .build());
    }
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<ProductDetailResponse>> updateProduct(@RequestBody ProductUpdateRequest request) {
        ProductDetailResponse response = productUpdateService.doProcess(request);
        return ResponseEntity.ok(ApiResponse.<ProductDetailResponse>builder()
                .status("SUCCESS")
                .code(200)
                .message("Product Update successfully")
                .data(response)
                .build());
    }
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> delete(@RequestBody ProductDetailRequest request) {
      productDeleteService.doProcess(request);

        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status("SUCCESS")
                .code(200)
                .message("Product delete successfully")
                .data(null)
                .build());
    }

}
