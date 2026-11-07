package com.ecommerce.product_service.domain.exception;

public class InvalidProductNotException extends RuntimeException{
    public InvalidProductNotException(String message) {
        super(message);
    }

}
