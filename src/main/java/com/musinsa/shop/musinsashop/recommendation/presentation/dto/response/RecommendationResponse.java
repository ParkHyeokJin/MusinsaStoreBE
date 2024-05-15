package com.musinsa.shop.musinsashop.recommendation.presentation.dto.response;

public record RecommendationResponse (String message){
    public static RecommendationResponse of (final String message){
        return new RecommendationResponse(message);
    }
}
