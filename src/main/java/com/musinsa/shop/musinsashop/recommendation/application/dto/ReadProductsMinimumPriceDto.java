package com.musinsa.shop.musinsashop.recommendation.application.dto;

import com.musinsa.shop.musinsashop.product.application.dto.ReadProductDto;

import java.util.List;

public record ReadProductsMinimumPriceDto(String brandName, List<ReadProductDto> productDtoList) {
    public static ReadProductsMinimumPriceDto of(final String brandName, final List<ReadProductDto> productDtoList){
        return new ReadProductsMinimumPriceDto(brandName, productDtoList);
    }
}
