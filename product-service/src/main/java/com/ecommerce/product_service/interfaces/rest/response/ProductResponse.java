package com.ecommerce.product_service.interfaces.rest.response;

import com.ecommerce.product_service.domain.model.Product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProductResponse (
        UUID id,
        String name,
        String description,
        String sku,
        BigDecimal price,
        boolean active,
        Instant createdAt,
        Instant updateAt
){
    public static ProductResponse from(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getSku().value(),
                product.getPrice(),
                product.isActive(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
