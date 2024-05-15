package com.musinsa.shop.musinsashop.brand.application.exception;

public class BrandNotFoundException extends IllegalArgumentException{
    public BrandNotFoundException(final String message) {
        super(message);
    }
}
