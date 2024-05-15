package com.musinsa.shop.musinsashop.brand.presentation.dto.response;

import com.musinsa.shop.musinsashop.brand.application.dto.ReadBrandDto;

public record BrandResponse (long id, String brandName) {
    public static BrandResponse from (final ReadBrandDto dto){
        return new BrandResponse(dto.id(), dto.brandName());
    }
}
