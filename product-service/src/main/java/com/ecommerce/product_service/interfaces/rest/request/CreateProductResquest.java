package com.ecommerce.product_service.interfaces.rest.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateProductResquest(
        @NotBlank(message = "O nome do produto é obrigatório")
        @Size(max = 150, message = "O nome do produto não pode ter mais de 150 caracteres")
        String name,

        @Size(max = 2500, message = "A descrição só pode até 2500 caracteres")
        String description,

        @NotBlank(message = "SKU é obrigatório")
        @Size(max= 50 ,message = "SKU não pode ter mais de 50 caracteres")
        String Sku,

        @NotNull(message = "O preço é obrigatório")
        @DecimalMin(value = "0.01", message = "O preço deve ser maior que 0")
        BigDecimal price
) {

}
