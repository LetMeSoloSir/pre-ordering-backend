package com.ordering.mvc.service.cart;

import com.ordering.mvc.model.cart.CartInfo;
import com.ordering.mvc.request.cart.CartAddRequest;
import com.ordering.mvc.service.common.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartAddService implements BaseService<CartAddRequest, Void> {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String CART_PREFIX = "cart:";

    private String getKey(UUID userId) {
        return CART_PREFIX + userId;
    }

    @Override
    public Void doProcess(CartAddRequest request) {
        String key = getKey(request.getUserId());

        List<CartInfo> items = (List<CartInfo>) redisTemplate.opsForValue().get(key);
        if (items == null) items = new ArrayList<>();

        boolean exists = false;

        for (CartInfo item : items) {
            if (item.getProductId().equals(request.getProductId())) {
                item.setQuantity(item.getQuantity() + request.getQuantity());
                exists = true;
            }
        }

        if (!exists) {
            items.add(new CartInfo(
                    request.getProductId(),
                    request.getProductName(),
                    request.getUnitPrice(),
                    request.getQuantity(),
                    request.getImageUrl()
            ));
        }

        redisTemplate.opsForValue().set(key, items);
        return null;
    }
}
