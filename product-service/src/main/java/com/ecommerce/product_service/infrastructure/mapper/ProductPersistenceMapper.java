package com.ecommerce.product_service.infrastructure.mapper;

import com.ecommerce.product_service.domain.model.Product;
import com.ecommerce.product_service.domain.model.Sku;
import com.ecommerce.product_service.infrastructure.persistence.ProductJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductPersistenceMapper {
    public ProductJpaEntity toEntity(Product product){
        return new ProductJpaEntity(
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
    public Product toDomain(ProductJpaEntity entity){
        return Product.restore(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                new Sku(entity.getSku()),
                entity.getPrice(),
                entity.isActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
