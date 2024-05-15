package com.musinsa.shop.musinsashop.category.application.dto;

import com.musinsa.shop.musinsashop.category.domain.Category;

public record ReadCategoryDto (long id, String categoryName)
{
    public static ReadCategoryDto of(final long id, final String categoryName){
        return new ReadCategoryDto(id, categoryName);
    }

    public static ReadCategoryDto from(final Category category){
        return new ReadCategoryDto(
                category.getId(),
                category.getCategoryName()
        );
    }
}
