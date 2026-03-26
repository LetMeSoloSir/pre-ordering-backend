package com.ordering.mvc.controller.order;

import com.ordering.mvc.request.order.CreateOrderRequest;
import com.ordering.mvc.response.common.ApiResponse;
import com.ordering.mvc.response.order.OrderDetailResponse;
import com.ordering.mvc.service.order.OrderCreateService;
import com.ordering.mvc.service.order.OrderHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderCreateService createOrderService;
    private final OrderHistoryService orderHistoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderDetailResponse>> create(
            @RequestBody CreateOrderRequest request) {

        OrderDetailResponse data = createOrderService.doProcess(request);

        return ResponseEntity.ok(
                ApiResponse.<OrderDetailResponse>builder()
                        .status("SUCCESS")
                        .code(200)
                        .message("Order created successfully")
                        .data(data)
                        .build()
        );

}
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<OrderDetailResponse>>> myOrders() {

        return ResponseEntity.ok(ApiResponse.<List<OrderDetailResponse>>builder()
                .status("SUCCESS")
                .code(200)
                .message("Fetched order history")
                .data(orderHistoryService.doProcess(null))
                .build());
    }
}

