package com.musinsa.shop.musinsashop.recommendation.presentation.dto.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record CategoryMinimumPriceRecommendationsResponse(long totalPrice, List<CategoryMinimumPriceRecommendationResponse> recommendations) {
    public static CategoryMinimumPriceRecommendationsResponse of(final long totalPrice, final List<CategoryMinimumPriceRecommendationResponse> recommendations){
        return new CategoryMinimumPriceRecommendationsResponse(totalPrice, recommendations);
    }

    public static String toJsonStringResponse(final long totalPrice, final List<CategoryMinimumPriceRecommendationResponse> recommendations) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        final Map<String, Object> response = new HashMap<>();

        final List<Object> products = new ArrayList<>();
        for(CategoryMinimumPriceRecommendationResponse recommendationResponse : recommendations){
            Map<String, Object> product = new HashMap<>();
            product.put("카테고리", recommendationResponse.categoryName());
            product.put("브랜드", recommendationResponse.brandName());
            product.put("가격", recommendationResponse.productPrice());
            products.add(product);
        }

        response.put("총액", totalPrice);
        response.put("최저가 상품 리스트", products);

        return objectMapper.writeValueAsString(response);
    }
}
