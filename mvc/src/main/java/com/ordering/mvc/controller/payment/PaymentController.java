package com.ordering.mvc.controller.payment;

import com.ordering.mvc.model.order.OrderInfo;
import com.ordering.mvc.repository.order.OrderRepository;
import com.ordering.mvc.service.payment.VnPayMockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final OrderRepository orderRepository;
    private final VnPayMockService vnPayMockService;

    @PostMapping("/vnpay/{orderId}")
    public ResponseEntity<?> payWithVnPay(@PathVariable UUID orderId) {

        OrderInfo order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        String redirectUrl = vnPayMockService.doProcess(order);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Payment success",
                        "redirectUrl", redirectUrl
                )
        );

    }
}

