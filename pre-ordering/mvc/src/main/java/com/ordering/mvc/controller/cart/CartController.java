package com.ordering.mvc.controller.cart;

import com.ordering.mvc.model.cart.CartInfo;
import com.ordering.mvc.request.cart.CartAddRequest;
import com.ordering.mvc.request.cart.CartGetRequest;
import com.ordering.mvc.request.cart.CartRemoveRequest;
import com.ordering.mvc.request.cart.CartUpdateRequest;
import com.ordering.mvc.response.common.ApiResponse;
import com.ordering.mvc.service.cart.CartAddService;
import com.ordering.mvc.service.cart.CartGetService;
import com.ordering.mvc.service.cart.CartRemoveService;
import com.ordering.mvc.service.cart.CartUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartAddService addService;
    private final CartUpdateService updateService;
    private final CartRemoveService removeService;
    private final CartGetService getService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Void>> addToCart(@RequestBody CartAddRequest request) {
        addService.doProcess(request);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status("SUCCESS")
                .code(200)
                .message("Add to Cart successfully")
                .data(null)
                .build());

    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Void>> updateCartItems(@RequestBody CartUpdateRequest request) {
        updateService.doProcess(request);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status("SUCCESS")
                .code(200)
                .message("Update Cart successfully")
                .data(null)
                .build());
    }

    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponse<Void>> removeCardItems(@RequestBody CartRemoveRequest request) {
        removeService.doProcess(request);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status("SUCCESS")
                .code(200)
                .message("Removed Cart successfully")
                .data(null)
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CartInfo>>> getAllCart(@RequestBody CartGetRequest request ) {
        List<CartInfo> data = getService.doProcess(request);

        return ResponseEntity.ok(ApiResponse.<List<CartInfo>>builder()
                .status("SUCCESS")
                .code(200)
                .message("Get Cart successfully")
                .data(data)
                .build());
    }
}


