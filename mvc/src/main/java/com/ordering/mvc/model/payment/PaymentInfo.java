package com.ordering.mvc.model.payment;

import com.ordering.mvc.model.common.BaseEntity;
import com.ordering.mvc.model.order.OrderInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "payment_id"))
public class PaymentInfo extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderInfo order;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "amount")
    private int amount;

    @Column(name = "transaction_no")
    private String transactionNo;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;
}
