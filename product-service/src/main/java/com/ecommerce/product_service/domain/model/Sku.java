package com.ecommerce.product_service.domain.model;

import java.util.Locale;

public record Sku (String value){
    public Sku {
        if(value == null || value.isBlank()){
            throw new IllegalArgumentException("SKU é obrigatório");
        }

        value = value.trim().toUpperCase(Locale.ROOT);

        if(value.length() > 50){
            throw new IllegalArgumentException("SKU não pode passar de 50 caracteres!");
        }
    }
}
