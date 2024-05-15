package com.musinsa.shop.musinsashop.recommendation.presentation.dto.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.shop.musinsashop.product.application.dto.ReadProductDto;
import com.musinsa.shop.musinsashop.recommendation.application.dto.ReadMinimumCategoryPrice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record MinimumPriceBrandProductsResponse(String brandName, List<ReadMinimumCategoryPrice> category, long totalPrice) {
    public static MinimumPriceBrandProductsResponse of(final String brandName, final List<ReadMinimumCategoryPrice> readMinimumCategoryPrices, final long totalPrice){
        return new MinimumPriceBrandProductsResponse(brandName, readMinimumCategoryPrices, totalPrice);
    }

    public static String toJsonStringResponse(final String brandName, final List<ReadMinimumCategoryPrice> readMinimumCategoryPrices, final long totalPrice) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> itemsMap = new HashMap<>();
        List<Map<String, Object>> priceMap = new ArrayList<>();
        for(ReadMinimumCategoryPrice categoryPrice : readMinimumCategoryPrices){
            Map<String, Object> product = new HashMap<>();
            product.put("카테고리", categoryPrice.category());
            product.put("가격", categoryPrice.price());
            priceMap.add(product);
        }
        itemsMap.put("브랜드", brandName);
        itemsMap.put("카테고리", priceMap);
        itemsMap.put("총액", totalPrice);
        response.put("최저가", itemsMap);
        return objectMapper.writeValueAsString(response);
    }
}
