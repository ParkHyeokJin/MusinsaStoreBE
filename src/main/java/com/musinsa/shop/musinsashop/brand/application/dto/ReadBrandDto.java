package com.musinsa.shop.musinsashop.brand.application.dto;

import com.musinsa.shop.musinsashop.brand.domain.Brand;

public record ReadBrandDto(long id, String brandName) {
    public static ReadBrandDto of(final long id, final String brandName){
        return new ReadBrandDto(id, brandName);
    }

    public static ReadBrandDto from(final Brand brand){
        return new ReadBrandDto(
            brand.getId(),
            brand.getBrandName()
        );
    }
}
