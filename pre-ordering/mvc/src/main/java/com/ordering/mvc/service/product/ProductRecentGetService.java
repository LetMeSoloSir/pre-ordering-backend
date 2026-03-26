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
public class ProductRecentGetService
        implements BaseService<UUID, List<ProductResponse>> {

    private final RedisTemplate<String, String> redisTemplate;
    private final ProductRepository productRepository;

    private static final String KEY_PREFIX = "recent:viewed:";

    @Override
    public List<ProductResponse> doProcess(UUID userId) {

        List<String> ids =
                redisTemplate.opsForList().range(KEY_PREFIX + userId, 0, 9);

        if (ids == null || ids.isEmpty()) return List.of();

        List<UUID> productIds = ids.stream()
                .map(UUID::fromString)
                .toList();

        return productRepository.findAllById(productIds)
                .stream()
                .map(p -> ProductResponse.builder()
                        .productId(p.getId())
                        .productName(p.getProductName())
                        .productPrice(p.getProductPrice())
                        .avatarUrl(p.getAvatarUrl())
                        .build())
                .toList();
    }
}

