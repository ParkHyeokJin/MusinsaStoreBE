package com.musinsa.shop.musinsashop.recommendation.presentation.dto.response;

import com.musinsa.shop.musinsashop.product.application.dto.ReadProductDto;

public record CategoryMinimumPriceRecommendationResponse(
        String categoryName,
        String brandName,
        long productPrice
) {
    public static CategoryMinimumPriceRecommendationResponse from(ReadProductDto dto){
        return new CategoryMinimumPriceRecommendationResponse(dto.categoryDto().categoryName(), dto.brandDto().brandName(), dto.productPrice());
    }
}
