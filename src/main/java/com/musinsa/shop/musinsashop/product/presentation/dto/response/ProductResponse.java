package com.musinsa.shop.musinsashop.product.presentation.dto.response;

import com.musinsa.shop.musinsashop.brand.application.dto.ReadBrandDto;
import com.musinsa.shop.musinsashop.category.application.dto.ReadCategoryDto;
import com.musinsa.shop.musinsashop.product.application.dto.ReadProductDto;

public record ProductResponse (String productName,
                               long productPrice,
                               ReadCategoryDto category,
                               ReadBrandDto brand){

    public static ProductResponse from (ReadProductDto readProductDto){
        return new ProductResponse(readProductDto.productName(), readProductDto.productPrice(), readProductDto.categoryDto(), readProductDto.brandDto());
    }
}
