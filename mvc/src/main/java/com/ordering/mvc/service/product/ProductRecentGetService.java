package com.ordering.mvc.service.product;

import com.ordering.mvc.repository.product.ProductRepository;
import com.ordering.mvc.response.product.ProductResponse;
import com.ordering.mvc.service.common.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductRecentGetService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ProductRepository productRepository;
    private static final String PREFIX = "recent:view:";

    public List<ProductResponse> doProcess(String userId) {
        List<UUID> ids =
                (List<UUID>) redisTemplate.opsForValue().get(PREFIX + userId);

        if (ids == null || ids.isEmpty()) return List.of();

        return productRepository.findAllById(ids)
                .stream()
                .map(ProductResponse::fromEntity)
                .toList();
    }
}

