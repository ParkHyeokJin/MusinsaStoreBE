package com.musinsa.shop.musinsashop.category.presentation.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record UpdateCategoryRequest(
        @NotEmpty(message = "대상 카테고리 명이 등록 되지 않았습니다.")
        String oldCategoryName ,
        @NotEmpty(message = "수정할 카테고리 명이 입력 되지 않았습니다.")
        String newCategoryName) {
}
