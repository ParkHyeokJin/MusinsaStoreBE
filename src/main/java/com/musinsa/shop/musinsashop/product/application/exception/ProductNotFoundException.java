package com.musinsa.shop.musinsashop.product.application.exception;

public class ProductNotFoundException extends IllegalArgumentException{
    public ProductNotFoundException(final String message) {
        super(message);
    }
}
