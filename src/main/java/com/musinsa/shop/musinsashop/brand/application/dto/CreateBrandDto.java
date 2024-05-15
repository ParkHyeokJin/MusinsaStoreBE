package com.musinsa.shop.musinsashop.brand.application.dto;

import com.musinsa.shop.musinsashop.brand.domain.Brand;

public record CreateBrandDto (String brandName){
    public Brand toEntity(final String brandName){
        return new Brand(brandName);
    }

    public static CreateBrandDto of(final String brandName){
        return new CreateBrandDto(brandName);
    }
}
