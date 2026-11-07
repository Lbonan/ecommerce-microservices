package com.ecommerce.product_service.domain.repository;

import com.ecommerce.product_service.domain.model.Product;
import com.ecommerce.product_service.domain.model.Sku;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(UUID id);

    List<Product> findAll();
    boolean existsById(Sku sku);
}
