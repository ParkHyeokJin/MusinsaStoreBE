package com.musinsa.shop.musinsashop.product.application.exception;

public class AlreadyProductException extends IllegalArgumentException{
    public AlreadyProductException(String message) {
        super(message);
    }
}
