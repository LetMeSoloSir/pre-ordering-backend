package com.ordering.mvc.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ordering.mvc.model.common.BaseEntity;
import com.ordering.mvc.model.payment.PaymentMethod;
import com.ordering.mvc.model.user.UserInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "order_id"))
public class OrderInfo extends BaseEntity implements Serializable {

    @Column(name = "order_number")
    @JsonProperty("orderNumber")
    private String orderNumber;

    @Column(name = "total_amount")
    private int totalAmount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserInfo userId;


    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItemInfo> items = new ArrayList<>();


    @Column(name = "shipping_address")
    @JsonProperty("shippingAddress")
    private String shippingAddress;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;


}
