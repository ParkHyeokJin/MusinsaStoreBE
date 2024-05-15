package com.musinsa.shop.musinsashop.recommendation.presentation;

import com.musinsa.shop.musinsashop.brand.application.dto.ReadBrandDto;
import com.musinsa.shop.musinsashop.category.application.dto.ReadCategoryDto;
import com.musinsa.shop.musinsashop.config.IntegrationTest;
import com.musinsa.shop.musinsashop.exception.GlobalExceptionHandler;
import com.musinsa.shop.musinsashop.product.application.dto.ReadProductDto;
import com.musinsa.shop.musinsashop.recommendation.application.dto.ReadMinimumCategoryPrice;
import com.musinsa.shop.musinsashop.recommendation.application.dto.ReadProductsMinimumPriceDto;
import com.musinsa.shop.musinsashop.recommendation.presentation.dto.response.CategoryMinMaxPriceProductsResponse;
import com.musinsa.shop.musinsashop.recommendation.presentation.dto.response.CategoryMinimumPriceRecommendationResponse;
import com.musinsa.shop.musinsashop.recommendation.presentation.dto.response.CategoryMinimumPriceRecommendationsResponse;
import com.musinsa.shop.musinsashop.recommendation.presentation.dto.response.MinimumPriceBrandProductsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RecommendationControllerTest extends IntegrationTest {

    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(recommendationController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
                .alwaysDo(print())
                .alwaysDo(restDocs)
                .build();
    }

    @Test
    void 카테고리별_최저가격의_상품을_추천받을수_있다() throws Exception {
        //given
        final long totalPrice = 3800L;
        final ReadProductDto readProductDto1 = new ReadProductDto(1L, "A_상의", 2000, new ReadCategoryDto(1L, "상의"), new ReadBrandDto(1L, "A"));
        final ReadProductDto readProductDto2 = new ReadProductDto(2L, "B_하의", 1800, new ReadCategoryDto(2L, "하의"), new ReadBrandDto(2L, "B"));
        final CategoryMinimumPriceRecommendationResponse response1 = CategoryMinimumPriceRecommendationResponse.from(readProductDto1);
        final CategoryMinimumPriceRecommendationResponse response2 = CategoryMinimumPriceRecommendationResponse.from(readProductDto2);
        final String jsonStringResponse = CategoryMinimumPriceRecommendationsResponse.toJsonStringResponse(totalPrice, List.of(response1, response2));

        given(recommendationService.findCategoryMinimumPriceRecommendation()).willReturn(List.of(readProductDto1, readProductDto2));

        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/recommendation/findCategoryMinimumPriceRecommendation")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.message", is(jsonStringResponse), String.class)
                ).andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("카테고리별 최저가격의 상품 정보와 총액")
                                )
                        )
                );
    }

    @Test
    void 모든_카테고리를_구매할경우_최저가격의_브랜드를_추천받을수_있다() throws Exception {
        final ReadProductDto readProductDto1 = new ReadProductDto(1L, "A_상의", 2000, new ReadCategoryDto(1L, "상의"), new ReadBrandDto(1L, "A"));
        final ReadProductDto readProductDto2 = new ReadProductDto(2L, "A_하의", 1800, new ReadCategoryDto(2L, "하의"), new ReadBrandDto(1L, "A"));
        final ReadProductDto readProductDto3 = new ReadProductDto(2L, "A_양말", 1800, new ReadCategoryDto(2L, "양말"), new ReadBrandDto(1L, "A"));
        final ReadProductsMinimumPriceDto readMinimumCategoryPrices = ReadProductsMinimumPriceDto.of("A", List.of(readProductDto1, readProductDto2, readProductDto3));
        final long totalPrice = readMinimumCategoryPrices.productDtoList().stream().mapToLong(ReadProductDto::productPrice).sum();
        final List<ReadMinimumCategoryPrice> readMinimumCategoryPriceList = readMinimumCategoryPrices.productDtoList().stream().map(product -> ReadMinimumCategoryPrice.of(product.categoryDto().categoryName(), product.productPrice())).toList();;
        String jsonStringResponse = MinimumPriceBrandProductsResponse.toJsonStringResponse(readMinimumCategoryPrices.brandName(), readMinimumCategoryPriceList, totalPrice);

        given(recommendationService.findMinimumPriceAllCategoriesBrandProducts()).willReturn(readMinimumCategoryPrices);

        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/recommendation/findMinimumPriceAllCategoriesBrandProducts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.message", is(jsonStringResponse), String.class)
                ).andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("모든 카테고리를 구매할때 최저가격의 브랜드와 상품 정보")
                                )
                        )
                );
    }

    @Test
    void 카테고리명으로_조회하여_최저가격의_상품과_최고가격의_상품을_추천받을수_있다() throws Exception {
        final ReadProductDto minPriceReadProductDto = new ReadProductDto(1L, "A_상의", 2000, new ReadCategoryDto(1L, "상의"), new ReadBrandDto(1L, "A"));
        final ReadProductDto maxPriceReadProductDto = new ReadProductDto(2L, "B_상의", 5800, new ReadCategoryDto(1L, "상의"), new ReadBrandDto(2L, "B"));
        final String response = CategoryMinMaxPriceProductsResponse.toJsonStringResponse("상의", minPriceReadProductDto, maxPriceReadProductDto);

        given(recommendationService.findMinimumPriceProductByCategoryName(anyString())).willReturn(minPriceReadProductDto);
        given(recommendationService.findMaximumPriceProductByCategoryName(anyString())).willReturn(maxPriceReadProductDto);

        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/recommendation/findMinimunAndMaximunPriceProductByCategoryName/상의")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.message", is(response), String.class)
                ).andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("카테고리에 해당되는 상품중 최고가격과 최저가격의 정보")
                                )
                        )
                );
    }
}