package com.ordering.mvc.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductRecentViewService {

    private static final int MAX_SIZE = 10;
    private static final String KEY_PREFIX = "recent:viewed:";

    private final RedisTemplate<String, String> redisTemplate;

    public void add(UUID userId, UUID productId) {
        String key = KEY_PREFIX + userId;

        redisTemplate.opsForList().remove(key, 1, productId.toString());

        redisTemplate.opsForList().leftPush(key, productId.toString());

        redisTemplate.opsForList().trim(key, 0, MAX_SIZE - 1);
    }
}
