package com.ordering.mvc.service.payment;

import com.ordering.mvc.model.order.OrderInfo;
import com.ordering.mvc.model.payment.PaymentInfo;
import com.ordering.mvc.model.payment.PaymentMethod;
import com.ordering.mvc.model.payment.PaymentStatus;
import com.ordering.mvc.repository.payment.PaymentRepository;
import com.ordering.mvc.service.common.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CodPaymentService implements BaseService<OrderInfo, Void> {

    private final PaymentRepository paymentRepository;

    @Override
    public Void doProcess(OrderInfo order) {

        PaymentInfo payment = new PaymentInfo();
        payment.setOrder(order);
        payment.setMethod(PaymentMethod.COD);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setAmount(order.getTotalAmount());

        paymentRepository.save(payment);
        return null;
    }
}

