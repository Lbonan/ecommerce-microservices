package com.ecommerce.product_service.infrastructure.repository;


import com.ecommerce.product_service.infrastructure.persistence.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataProductRepository extends JpaRepository<ProductJpaEntity, UUID> {
    boolean existsBySku(String sku);
}
