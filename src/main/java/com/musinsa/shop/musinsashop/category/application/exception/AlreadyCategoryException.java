package com.musinsa.shop.musinsashop.category.application.exception;

public class AlreadyCategoryException extends IllegalArgumentException{
    public AlreadyCategoryException(final String message) {
        super(message);
    }
}
