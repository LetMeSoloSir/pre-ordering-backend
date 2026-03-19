package com.ordering.mvc.repository.order;

import com.ordering.mvc.model.order.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderInfo, UUID> {
    List<OrderInfo> findByCreatedByOrderByCreatedAtDesc(String userId);

    @Query("SELECT COUNT(o) FROM OrderInfo o")
    long countOrders();

    @Query("SELECT SUM(o.totalAmount) FROM OrderInfo o")
    Double sumRevenue();

    @Query("""
                SELECT FUNCTION('DATE', o.createdAt), SUM(o.totalAmount)
                FROM OrderInfo o
                WHERE o.createdAt BETWEEN :from AND :to
                GROUP BY FUNCTION('DATE', o.createdAt)
            """)
    List<Object[]> revenueByDate(LocalDateTime from, LocalDateTime to);

}
