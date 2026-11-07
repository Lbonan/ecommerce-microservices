package com.ecommerce.product_service.domain.exception;

import com.ecommerce.product_service.domain.model.Sku;

public class DuplicateSkuException extends RuntimeException {
    public DuplicateSkuException(Sku sku) {
        super("O produto com SKU " + sku.value() + "já existe");
    }
}
