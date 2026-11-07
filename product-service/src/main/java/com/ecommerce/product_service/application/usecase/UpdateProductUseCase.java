package com.ecommerce.product_service.application.usecase;

import com.ecommerce.product_service.domain.exception.ProductNotFoundException;
import com.ecommerce.product_service.domain.model.Product;
import com.ecommerce.product_service.domain.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class UpdateProductUseCase {
    private ProductRepository productRepository;

    public UpdateProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product execute(
            UUID id,
            String name,
            String description,
            BigDecimal price
    )
    {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        product.updateDetails(name, description);
        product.changePrice(price);

        return productRepository.save(product);
    }
}
