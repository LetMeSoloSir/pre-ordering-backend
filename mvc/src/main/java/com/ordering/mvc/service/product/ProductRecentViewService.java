package com.ordering.mvc.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductRecentViewService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String PREFIX = "recent:view:";

    public void add(String userId, UUID productId) {
        String key = PREFIX + userId;

        List<UUID> list = (List<UUID>) redisTemplate.opsForValue().get(key);
        if (list == null) list = new ArrayList<>();

        list.remove(productId);
        list.add(0, productId);

        if (list.size() > 10) {
            list = list.subList(0, 10);
        }

        redisTemplate.opsForValue().set(key, list);
    }
}
