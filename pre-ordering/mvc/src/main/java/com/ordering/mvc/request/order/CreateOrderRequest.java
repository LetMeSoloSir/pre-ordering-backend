package com.ordering.mvc.request.order;

import com.ordering.mvc.model.payment.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
    private String shippingAddress;
    private PaymentMethod paymentMethod;
}
