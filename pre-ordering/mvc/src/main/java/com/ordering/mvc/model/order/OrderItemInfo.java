package com.ordering.mvc.model.order;

import com.ordering.mvc.model.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "order_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "order_item_id"))
public class OrderItemInfo extends BaseEntity implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderInfo order;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "unit_price")
    private int unitPrice;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "total_price")
    private int totalPrice;
}

