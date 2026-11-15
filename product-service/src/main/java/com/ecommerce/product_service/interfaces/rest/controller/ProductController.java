package com.ecommerce.product_service.interfaces.rest.controller;

import com.ecommerce.product_service.application.usecase.CreateProductUseCase;
import com.ecommerce.product_service.application.usecase.DeactivateProductUseCase;
import com.ecommerce.product_service.application.usecase.GetProductUseCase;
import com.ecommerce.product_service.application.usecase.UpdateProductUseCase;
import com.ecommerce.product_service.domain.model.Product;
import com.ecommerce.product_service.interfaces.rest.request.CreateProductResquest;
import com.ecommerce.product_service.interfaces.rest.request.UpdateProductRequest;
import com.ecommerce.product_service.interfaces.rest.response.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final GetProductUseCase getProductUseCase;
    private final DeactivateProductUseCase deactivateProductUseCase;
    public ProductController(
            CreateProductUseCase createProductUseCase,
            GetProductUseCase getProductUseCase,
            UpdateProductUseCase updateProductUseCase,
            DeactivateProductUseCase deactivateProductUseCase
    ){
        this.createProductUseCase = createProductUseCase;
        this.getProductUseCase = getProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.deactivateProductUseCase = deactivateProductUseCase;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(
            @Valid @RequestBody CreateProductResquest resquest
            ) {
        Product product = createProductUseCase.execute(
                resquest.name(),
                resquest.description(),
                resquest.Sku(),
                resquest.price()
        );
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();
        return ResponseEntity.created(location).body(ProductResponse.from(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(
            @PathVariable UUID id
    ) {
        Product product = getProductUseCase.execute(id);

        return ResponseEntity.ok(ProductResponse.from(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateProductRequest request
            ) {
        Product product = updateProductUseCase.execute(
                id,
                request.name(),
                request.description(),
                request.price()
        );
        return ResponseEntity.ok(ProductResponse.from(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(
            @PathVariable UUID id
    ) {
        deactivateProductUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
