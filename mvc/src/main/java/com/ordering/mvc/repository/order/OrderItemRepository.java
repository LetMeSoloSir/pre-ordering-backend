package com.ordering.mvc.repository.order;

import com.ordering.mvc.model.order.OrderItemInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItemInfo, UUID> {
}
