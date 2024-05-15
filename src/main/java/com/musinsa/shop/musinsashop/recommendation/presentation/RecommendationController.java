package com.musinsa.shop.musinsashop.recommendation.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.shop.musinsashop.product.application.dto.ReadProductDto;
import com.musinsa.shop.musinsashop.recommendation.application.RecommendationService;
import com.musinsa.shop.musinsashop.recommendation.application.dto.ReadMinimumCategoryPrice;
import com.musinsa.shop.musinsashop.recommendation.application.dto.ReadProductsMinimumPriceDto;
import com.musinsa.shop.musinsashop.recommendation.presentation.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommendation")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping(value = "/findCategoryMinimumPriceRecommendation")
    public ResponseEntity<RecommendationResponse> findCategoryMinimumPriceRecommendation() throws JsonProcessingException {
        final List<ReadProductDto> categoryMinimumPriceRecommendation = recommendationService.findCategoryMinimumPriceRecommendation();
        final long totalPrice = categoryMinimumPriceRecommendation.stream().mapToLong(ReadProductDto::productPrice).sum();
        final List<CategoryMinimumPriceRecommendationResponse> categoryMinimumPriceRecommendationResponses = categoryMinimumPriceRecommendation.stream().map(CategoryMinimumPriceRecommendationResponse::from).toList();
        final String response = CategoryMinimumPriceRecommendationsResponse.toJsonStringResponse(totalPrice, categoryMinimumPriceRecommendationResponses);
        return ResponseEntity.ok().body(RecommendationResponse.of(response));
    }

    @GetMapping(value = "/findMinimumPriceAllCategoriesBrandProducts")
    public ResponseEntity<RecommendationResponse> findMinimumPriceAllCategoriesBrandProducts() throws JsonProcessingException {
        final ReadProductsMinimumPriceDto readProductsMinimumPriceDto = recommendationService.findMinimumPriceAllCategoriesBrandProducts();
        final long totalPrice = readProductsMinimumPriceDto.productDtoList().stream().mapToLong(ReadProductDto::productPrice).sum();
        final List<ReadMinimumCategoryPrice> readMinimumCategoryPrices = readProductsMinimumPriceDto.productDtoList().stream().map(product -> ReadMinimumCategoryPrice.of(product.categoryDto().categoryName(), product.productPrice())).toList();
        final String response = MinimumPriceBrandProductsResponse.toJsonStringResponse(readProductsMinimumPriceDto.brandName(), readMinimumCategoryPrices, totalPrice);
        return ResponseEntity.ok(RecommendationResponse.of(response));
    }

    @GetMapping(value = "/findMinimunAndMaximunPriceProductByCategoryName/{categoryName}")
    public ResponseEntity<RecommendationResponse> findMinimunAndMaximunPriceProductByCategoryName(@PathVariable("categoryName") String categoryName) throws JsonProcessingException {
        final ReadProductDto minimumPriceProductDto = recommendationService.findMinimumPriceProductByCategoryName(categoryName);
        final ReadProductDto maximumPriceProductDto = recommendationService.findMaximumPriceProductByCategoryName(categoryName);
        final String response = CategoryMinMaxPriceProductsResponse.toJsonStringResponse(categoryName, minimumPriceProductDto, maximumPriceProductDto);
        return ResponseEntity.ok(RecommendationResponse.of(response));
    }
}
