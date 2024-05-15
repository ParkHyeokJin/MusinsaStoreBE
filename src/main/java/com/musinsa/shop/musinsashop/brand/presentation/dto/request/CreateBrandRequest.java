package com.musinsa.shop.musinsashop.brand.presentation.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record CreateBrandRequest(
        @NotEmpty(message = "브랜드 명이 입력 되지 않았습니다.")
        String brandName
) {
}
