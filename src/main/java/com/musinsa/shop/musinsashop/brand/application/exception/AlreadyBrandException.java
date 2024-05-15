package com.musinsa.shop.musinsashop.brand.application.exception;

public class AlreadyBrandException extends IllegalArgumentException {
    public AlreadyBrandException(final String message){
        super(message);
    }
}
