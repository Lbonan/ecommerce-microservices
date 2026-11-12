package com.ecommerce.product_service.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_products",
       uniqueConstraints = {
        @UniqueConstraint(name = "uk_products_sku", columnNames = "sku")
       }
)
public class ProductJpaEntity {
    @Id
    private UUID id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, length = 50)
    private String Sku;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    protected ProductJpaEntity() {}

    public ProductJpaEntity(
            UUID id,
            String name,
            String description,
            String sku,
            BigDecimal price,
            boolean active,
            Instant createdAt,
            Instant updatedAt
    ){
        this.id = id;
        this.name = name;
        this.description = description;
        this.Sku = sku;
        this.price = price;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
