package com.musinsa.shop.musinsashop.category.application.exception;

public class CategoryNotFoundException extends IllegalArgumentException{
    public CategoryNotFoundException(final String message) {
        super(message);
    }
}
