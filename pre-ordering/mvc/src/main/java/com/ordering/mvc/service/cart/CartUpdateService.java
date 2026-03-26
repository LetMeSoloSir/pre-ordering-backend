package com.ordering.mvc.service.cart;

import com.ordering.mvc.model.cart.CartInfo;
import com.ordering.mvc.request.cart.CartUpdateRequest;
import com.ordering.mvc.service.common.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartUpdateService implements BaseService<CartUpdateRequest, Void> {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String CART_PREFIX = "cart:";

    private String getKey(UUID userId) {
        return CART_PREFIX + userId;
    }

    @Override
    public Void doProcess(CartUpdateRequest request) {

        List<CartInfo> items =
                (List<CartInfo>) redisTemplate.opsForValue().get(getKey(request.getUserId()));

        if (items == null) return null;

        for (CartInfo item : items) {
            if (item.getProductId().equals(request.getProductId())) {
                item.setQuantity(request.getQuantity());
            }
        }

        redisTemplate.opsForValue().set(getKey(request.getUserId()), items);
        return null;
    }
}

