package com.ecommerce.product_service.application.usecase;

import com.ecommerce.product_service.domain.exception.DuplicateSkuException;
import com.ecommerce.product_service.domain.model.Product;
import com.ecommerce.product_service.domain.model.Sku;
import com.ecommerce.product_service.domain.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CreateProductUseCase {
    private ProductRepository productRepository;

    public CreateProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product execute(
            String name,
            String description,
            String sku,
            BigDecimal price
    ){
        Sku productSku = new Sku(sku);

        if (productRepository.existsBySku(productSku)) {
            throw new DuplicateSkuException(productSku);
        }

        Product product = Product.create(
                name,
                description,
                productSku,
                price
        );

        return  productRepository.save(product);
    }
}
