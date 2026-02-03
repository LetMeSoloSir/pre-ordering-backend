package com.ordering.mvc.response.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {

    private String orderNumber;
    private int totalAmount;
    private String status;
    private String paymentMethod;
    private String shippingAddress;
    private LocalDateTime createdAt;

    private List<OrderItemResponse> items;
}
