package com.ordering.mvc.service.payment;

import com.ordering.mvc.model.order.OrderInfo;
import com.ordering.mvc.model.order.OrderStatus;
import com.ordering.mvc.model.payment.PaymentInfo;
import com.ordering.mvc.model.payment.PaymentMethod;
import com.ordering.mvc.model.payment.PaymentStatus;
import com.ordering.mvc.repository.payment.PaymentRepository;
import com.ordering.mvc.service.common.BaseService;
import com.ordering.mvc.service.telegram.TelegramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VnPayMockService implements BaseService<OrderInfo, String> {

    private final PaymentRepository paymentRepository;
    private final TelegramService telegramService;

    @Override
    public String doProcess(OrderInfo order) {

        PaymentInfo payment = new PaymentInfo();
        payment.setOrder(order);
        payment.setMethod(PaymentMethod.VNPAY);
        payment.setStatus(PaymentStatus.PAID);
        payment.setAmount(order.getTotalAmount());
        payment.setTransactionNo("VNPAY-" + System.currentTimeMillis());
        payment.setPaidAt(LocalDateTime.now());

        paymentRepository.save(payment);

        order.setStatus(OrderStatus.PAID);
        order.setPaidAt(LocalDateTime.now());

        telegramService.sendOrderPaidMessage(order);

        return "https://sandbox.vnpay.vn/mock-success";
    }
}
