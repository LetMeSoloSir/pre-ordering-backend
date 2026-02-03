package com.ordering.mvc.service.cart;

import com.ordering.mvc.model.cart.CartInfo;
import com.ordering.mvc.request.cart.CartGetRequest;
import com.ordering.mvc.service.common.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartGetService implements BaseService<CartGetRequest, List<CartInfo>> {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String CART_PREFIX = "cart:";

    private String getKey(String userId) {
        return CART_PREFIX + userId;
    }

    @Override
    public List<CartInfo> doProcess(CartGetRequest request) {
        Object data = redisTemplate.opsForValue().get(getKey(request.getUserId()));
        if (data == null) return new ArrayList<>();
        return (List<CartInfo>) data;
    }
}

