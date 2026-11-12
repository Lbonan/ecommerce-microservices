package com.ecommerce.product_service.infrastructure.repository;

import com.ecommerce.product_service.domain.model.Product;
import com.ecommerce.product_service.domain.model.Sku;
import com.ecommerce.product_service.infrastructure.mapper.ProductPersistenceMapper;
import com.ecommerce.product_service.infrastructure.persistence.ProductJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProductRepositortImpl {
    private final SpringDataProductRepository jpaRepository;
    private final ProductPersistenceMapper mapper;

    public ProductRepositortImpl(SpringDataProductRepository jpaProductRepository, ProductPersistenceMapper mapper) {
        this.jpaRepository = jpaProductRepository;
        this.mapper = mapper;
    }


    public Product save(Product product){
        ProductJpaEntity entity = mapper.toEntity(product);
        ProductJpaEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }


    public Optional<Product> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }


    public List<Product> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }


    public boolean existsBySku(Sku sku) {
        return jpaRepository.existsBySku(sku.value());
    }


}
