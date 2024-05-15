package com.musinsa.shop.musinsashop.recommendation.presentation.dto.response;

public record RecommendationResponse (String message){
    public static RecommendationResponse of (String message){
        return new RecommendationResponse(message);
    }
}
