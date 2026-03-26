package com.ordering.mvc.service.order;

import com.ordering.mvc.repository.order.OrderRepository;
import com.ordering.mvc.response.order.OrderDetailResponse;
import com.ordering.mvc.service.common.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderHistoryService implements BaseService<Void, List<OrderDetailResponse>> {

    private final OrderRepository orderRepository;

    @Override
    public List<OrderDetailResponse> doProcess(Void unused) {

        String userId = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        return orderRepository.findByCreatedByOrderByCreatedAtDesc(userId)
                .stream()
                .map(order -> OrderDetailResponse.builder()
                        .orderNumber(order.getOrderNumber())
                        .totalAmount(order.getTotalAmount())
                        .status(order.getStatus().name())
                        .paymentMethod(order.getPaymentMethod().getDescription())
                        .createdAt(order.getCreatedAt())
                        .build()
                ).toList();
    }
}

