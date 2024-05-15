package com.musinsa.shop.musinsashop.recommendation.presentation.dto.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.shop.musinsashop.product.application.dto.ReadProductDto;

import java.util.HashMap;
import java.util.Map;

public record CategoryMinMaxPriceProductsResponse(String categoryName,
                                                  ReadProductDto minimumPriceProductDto,
                                                  ReadProductDto maximumPriceProductDto
                                                  ) {
    public static CategoryMinMaxPriceProductsResponse of(final String categoryName, final ReadProductDto minimumPriceProductDto, final ReadProductDto maximumPriceProductDto){
        return new CategoryMinMaxPriceProductsResponse(categoryName, minimumPriceProductDto, maximumPriceProductDto);
    }

    public static String toJsonStringResponse(final String categoryName, final ReadProductDto minimumPriceProductDto, final ReadProductDto maximumPriceProductDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> minProduct = new HashMap<>();
        Map<String, Object> maxProduct = new HashMap<>();

        minProduct.put("브랜드", minimumPriceProductDto.brandDto().brandName());
        minProduct.put("가격", minimumPriceProductDto.productPrice());

        maxProduct.put("브랜드", maximumPriceProductDto.brandDto().brandName());
        maxProduct.put("가격", maximumPriceProductDto.productPrice());

        response.put("카테고리", categoryName);

        response.put("최저가", minProduct);
        response.put("최고가", maxProduct);

        return objectMapper.writeValueAsString(response);
    }
}
