package com.ecommerce.product_service.domain.model;


import com.ecommerce.product_service.domain.exception.InvalidProductNotException;
import lombok.Getter;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
public class Product {
    private final UUID id;
    private String name;
    private  String description;
    private final Sku sku;
    private  BigDecimal price;
    private boolean active;
    private final Instant createdAt;
    private Instant updatedAt;

    private Product(
            UUID id,
            String name,
            String description,
            Sku sku,
            BigDecimal price,
            boolean active,
            Instant createdAt,
            Instant updatedAt
            ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sku = sku;
        this.price = price;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Product create(
            String name,
            String description,
            Sku sku,
            BigDecimal price
    ){
        String validatedName = validateName(name);
        String normalizedDescription = normalizedDescription(description);
        BigDecimal validatedPrice = validatePrice(price);

        if(sku == null){
            throw new IllegalArgumentException("SKU is required");
        }

        Instant now = Instant.now();

        return new Product(
                UUID.randomUUID(),
                validatedName,
                normalizedDescription,
                sku,
                validatedPrice,
                true,
                now,
                now
        );
    }

    public void updateDetails(String name, String description){
        String validatedName = validateName(name);
        String normalizedDescription = normalizedDescription(description);

        this.name = validatedName;
        this.description = normalizedDescription;
        this.updatedAt = Instant.now();
    }

    public void changePrice(BigDecimal newPrice){
        BigDecimal validatedPrice = validatePrice(newPrice);

        this.price = validatedPrice;
        this.updatedAt = Instant.now();
    }

    public void deactivate(){
        if(this.active){
            this.active = false;
            this.updatedAt = Instant.now();
        }
    }

    public void activate(){
        if(!this.active){
            this.active = true;
            this.updatedAt = Instant.now();
        }
    }

    private static String validateName(String name) {
        if(name == null || name.isBlank()){
            throw new InvalidProductNotException("Produto é obrigatório");
        }
        return name.trim();
    }

    private static String normalizedDescription(String description){
        if(description == null || description.isBlank()){
            return null;
        }
        return description.trim();
    }

    private static BigDecimal validatePrice(BigDecimal price){
        if(price == null || price.compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidProductNotException("Produto deve ter um preço maior que zero");
        }
        return price;
    }
}
