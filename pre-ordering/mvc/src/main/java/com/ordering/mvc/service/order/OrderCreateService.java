package com.ordering.mvc.service.order;

import com.ordering.mvc.exception.CartIsEmptyException;
import com.ordering.mvc.model.cart.CartInfo;
import com.ordering.mvc.model.order.OrderInfo;
import com.ordering.mvc.model.order.OrderItemInfo;
import com.ordering.mvc.model.order.OrderStatus;
import com.ordering.mvc.repository.order.OrderItemRepository;
import com.ordering.mvc.repository.order.OrderRepository;
import com.ordering.mvc.request.cart.CartGetRequest;
import com.ordering.mvc.request.order.CreateOrderRequest;
import com.ordering.mvc.response.order.OrderDetailResponse;
import com.ordering.mvc.response.order.OrderItemResponse;
import com.ordering.mvc.service.cart.CartClearService;
import com.ordering.mvc.service.cart.CartGetService;
import com.ordering.mvc.service.common.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderCreateService implements BaseService<CreateOrderRequest, OrderDetailResponse> {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartGetService cartGetService;
    private final CartClearService cartClearService;

    @Override
    @Transactional
    public OrderDetailResponse doProcess(CreateOrderRequest request) {

        String userId = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        List<CartInfo> cartItems =
                cartGetService.doProcess(new CartGetRequest(userId));
        if (cartItems == null || cartItems.isEmpty()) {
            throw new CartIsEmptyException();
        }

        OrderInfo order = new OrderInfo();
        order.setOrderNumber("ORD-" + System.currentTimeMillis());
        order.setShippingAddress(request.getShippingAddress());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setStatus(OrderStatus.PENDING);

        order = orderRepository.save(order);

        int totalAmount = 0;
        List<OrderItemResponse> itemResponses = new ArrayList<>();

        for (CartInfo cartItem : cartItems) {

            int unitPrice = cartItem.getUnitPrice().intValue();
            int itemTotal = unitPrice * cartItem.getQuantity();

            OrderItemInfo orderItem = new OrderItemInfo();
            orderItem.setOrder(order);
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setProductName(cartItem.getProductName());
            orderItem.setUnitPrice(unitPrice);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(itemTotal);

            orderItemRepository.save(orderItem);

            totalAmount += itemTotal;

            OrderItemResponse itemRes = new OrderItemResponse();
            itemRes.setProductId(cartItem.getProductId());
            itemRes.setProductName(cartItem.getProductName());
            itemRes.setUnitPrice(unitPrice);
            itemRes.setQuantity(cartItem.getQuantity());
            itemRes.setTotalPrice(itemTotal);

            itemResponses.add(itemRes);
        }

        order.setTotalAmount(totalAmount);

        cartClearService.doProcess(userId);

        OrderDetailResponse response = new OrderDetailResponse();
        response.setOrderNumber(order.getOrderNumber());
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus().name());
        response.setPaymentMethod(order.getPaymentMethod().getDescription());
        response.setShippingAddress(order.getShippingAddress());
        response.setCreatedAt(order.getCreatedAt());
        response.setItems(itemResponses);

        return response;
    }
}
