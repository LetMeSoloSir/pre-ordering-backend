package com.ordering.mvc.repository.order;

import com.ordering.mvc.model.order.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderInfo, UUID> {
    List<OrderInfo> findByCreatedByOrderByCreatedAtDesc(String userId);
}
