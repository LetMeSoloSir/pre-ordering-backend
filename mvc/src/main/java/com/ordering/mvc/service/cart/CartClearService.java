package com.ordering.mvc.service.cart;

import com.ordering.mvc.service.common.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartClearService implements BaseService<String, Void> {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String CART_PREFIX = "cart:";

    private String getKey(String userId) {
        return CART_PREFIX + userId;
    }

    @Override
    public Void doProcess(String userId) {
        redisTemplate.delete(getKey(userId));
        return null;
    }
}
