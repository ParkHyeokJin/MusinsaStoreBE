package com.musinsa.shop.musinsashop.category.application.dto;

import com.musinsa.shop.musinsashop.category.domain.Category;

public record CreateCategoryDto (String categoryName) {

    public static CreateCategoryDto of(final String categoryName){
        return new CreateCategoryDto(categoryName);
    }

    public Category toEntity(final String categoryName){
        return new Category(categoryName);
    }
}
