package com.musinsa.shop.musinsashop.product.presentation.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateProductRequest(
        @NotEmpty(message = "상품명이 입력 되지 않았습니다.")
        String productName,
        @NotNull(message = "가격이 입력 되지 않았습니다.")
        @Positive(message = "가격은 양수입니다.")
        long productPrice,

        @NotNull(message = "카테고리가 입력 되지 않았습니다.")
        @Positive(message = "카테고리 아이디는 양수입니다.")
        long categoryId,
        @NotNull(message = "브랜드가 입력 되지 않았습니다.")
        @Positive(message = "브랜드 아이디는 양수입니다.")
        long brandId
) {
}
