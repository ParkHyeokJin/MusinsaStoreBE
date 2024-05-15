package com.musinsa.shop.musinsashop.category.presentation.dto.response;

import com.musinsa.shop.musinsashop.category.application.dto.ReadCategoryDto;

public record CategoryResponse(long id, String categoryName)
{
    public static CategoryResponse from(final ReadCategoryDto dto){
        return new CategoryResponse(dto.id(), dto.categoryName());
    }
}
