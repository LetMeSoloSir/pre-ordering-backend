package com.ordering.mvc.repository.payment;

import com.ordering.mvc.model.payment.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentInfo, Void> {
}
