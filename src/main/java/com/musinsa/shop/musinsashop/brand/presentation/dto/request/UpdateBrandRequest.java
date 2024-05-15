package com.musinsa.shop.musinsashop.brand.presentation.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record UpdateBrandRequest (
        @NotEmpty(message = "대상 브랜드명이 입력 되지 않았습니다.")
        String oldBrandName,
        @NotEmpty(message = "변경할 브랜드명이 입력 되지 않았습니다.")
        String newBrandName
){
}
