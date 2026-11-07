package com.ecommerce.product_service.application.usecase;

import com.ecommerce.product_service.domain.exception.ProductNotFoundException;
import com.ecommerce.product_service.domain.model.Product;
import com.ecommerce.product_service.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeactivateProductUseCase {
    private ProductRepository productRepository;
    public DeactivateProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void execute(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        product.deactivate();
        productRepository.save(product);
    }
}
