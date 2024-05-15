package com.musinsa.shop.musinsashop.category.presentation.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record CreateCategoryRequest(
        @NotEmpty(message = "카테고리명이 입력 되지 않았습니다.")
        String categoryName
) {
}
