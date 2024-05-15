package com.musinsa.shop.musinsashop.brand.presentation;

import com.musinsa.shop.musinsashop.brand.application.dto.CreateBrandDto;
import com.musinsa.shop.musinsashop.brand.application.dto.ReadBrandDto;
import com.musinsa.shop.musinsashop.brand.application.exception.AlreadyBrandException;
import com.musinsa.shop.musinsashop.brand.application.exception.BrandNotFoundException;
import com.musinsa.shop.musinsashop.config.IntegrationTest;
import com.musinsa.shop.musinsashop.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BrandControllerTest extends IntegrationTest {

    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(brandController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
                .alwaysDo(print())
                .alwaysDo(restDocs)
                .build();
    }

    @Test
    void 브랜드를_생성할수_있다() throws Exception {
        final CreateBrandDto createBrandDto = new CreateBrandDto("TEST_BRAND");
        final ReadBrandDto readBrandDto = new ReadBrandDto(1L, "TEST_BRAND");

        given(brandService.createBrand(createBrandDto)).willReturn(readBrandDto);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBrandDto)))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id", is(readBrandDto.id()), Long.class),
                        jsonPath("$.brandName", is(readBrandDto.brandName()))
                        )
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("브랜드 ID"),
                                        fieldWithPath("brandName").type(JsonFieldType.STRING).description("브랜드 명")
                                )
                        )
                );
    }

    @Test
    void 브랜드생성시_중복된_브랜드명이면_400_오류를_반환한다() throws Exception {
        final CreateBrandDto createBrandDto = new CreateBrandDto("TEST_BRAND");
        final AlreadyBrandException alreadyBrandException = new AlreadyBrandException("중복된 브랜드 입니다.");

        given(brandService.createBrand(createBrandDto)).willThrow(alreadyBrandException);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/brand")
                .content(objectMapper.writeValueAsString(createBrandDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                status().isBadRequest(),
                jsonPath("$.message", is(alreadyBrandException.getMessage()))
        );
    }

    @Test
    void 브랜드명을_수정할수_있다() throws Exception{
        Map<String, Object> updateRequest = new HashMap<>();
        updateRequest.put("oldBrandName", "OLD_BRAND");
        updateRequest.put("newBrandName", "NEW_BRAND");

        final ReadBrandDto readBrandDto = new ReadBrandDto(1L, "NEW_BRAND");
        given(brandService.updateBrand("OLD_BRAND", "NEW_BRAND")).willReturn(readBrandDto);

        mockMvc.perform(RestDocumentationRequestBuilders.put("/brand")
                        .content(objectMapper.writeValueAsString(updateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                    status().isOk(),
                    jsonPath("$.id", is(readBrandDto.id()), Long.class),
                    jsonPath("$.brandName", is(readBrandDto.brandName()))
                )
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("브랜드 ID"),
                                        fieldWithPath("brandName").type(JsonFieldType.STRING).description("브랜드 명")
                                )
                        )
                );
    }

    @Test
    void 브랜드_수정시_브랜드가_없는경우_404_오류를_반환한다() throws Exception{
        Map<String, Object> updateRequest = new HashMap<>();
        updateRequest.put("oldBrandName", "OLD_BRAND");
        updateRequest.put("newBrandName", "NEW_BRAND");

        final BrandNotFoundException brandNotFoundException = new BrandNotFoundException("대상 브랜드가 없습니다.");
        given(brandService.updateBrand("OLD_BRAND", "NEW_BRAND")).willThrow(brandNotFoundException);

        mockMvc.perform(RestDocumentationRequestBuilders.put("/brand")
                .content(objectMapper.writeValueAsString(updateRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                status().isNotFound(),
                jsonPath("$.message", is(brandNotFoundException.getMessage()))
        );
    }

    @Test
    void 브랜드명으로_조회할수_있다() throws Exception{
        final ReadBrandDto readBrandDto = new ReadBrandDto(1L, "TEST_BRAND");
        given(brandService.selectBrandByName("TEST_BRAND")).willReturn(readBrandDto);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/brand/findBrandName/TEST_BRAND")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id", is(readBrandDto.id()), Long.class),
                        jsonPath("$.brandName", is(readBrandDto.brandName()))
                )
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("브랜드 ID"),
                                        fieldWithPath("brandName").type(JsonFieldType.STRING).description("브랜드 명")
                                )
                        )
                );
    }

    @Test
    void 브랜드_아이디로_조회할수_있다() throws Exception{
        Map<String, Object> request = new HashMap<>();
        request.put("brandName", "TEST_BRAND");

        final ReadBrandDto readBrandDto = new ReadBrandDto(1L, "TEST_BRAND");
        given(brandService.selectBrand(1L)).willReturn(readBrandDto);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/brand/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id", is(readBrandDto.id()), Long.class),
                        jsonPath("$.brandName", is(readBrandDto.brandName()))
                )
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("브랜드 ID"),
                                        fieldWithPath("brandName").type(JsonFieldType.STRING).description("브랜드 명")
                                )
                        )
                );
    }

    @Test
    void 브랜드_리스트를_조회할수_있다() throws Exception{
        final ReadBrandDto readBrandDto1 = new ReadBrandDto(1L, "TEST_BRAND");
        final ReadBrandDto readBrandDto2 = new ReadBrandDto(2L, "TEST_BRAND");
        final ReadBrandDto readBrandDto3 = new ReadBrandDto(3L, "TEST_BRAND");

        given(brandService.selectBrandAll(0, 10)).willReturn(List.of(readBrandDto1, readBrandDto2, readBrandDto3));

        mockMvc.perform(RestDocumentationRequestBuilders.get("/brand/brands")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.[0].id", is(readBrandDto1.id()), Long.class),
                        jsonPath("$.[0].brandName", is(readBrandDto1.brandName())),
                        jsonPath("$.[1].id", is(readBrandDto2.id()), Long.class),
                        jsonPath("$.[1].brandName", is(readBrandDto2.brandName())),
                        jsonPath("$.[2].id", is(readBrandDto3.id()), Long.class),
                        jsonPath("$.[2].brandName", is(readBrandDto3.brandName()))
                )
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("브랜드 ID"),
                                        fieldWithPath("[].brandName").type(JsonFieldType.STRING).description("브랜드 명")
                                )
                        )
                );
    }

    @Test
    void 브랜드를_삭제할수_있다() throws Exception {

        willDoNothing().given(brandService).deleteBrand(anyLong());

        mockMvc.perform(RestDocumentationRequestBuilders.delete("/brand/{brandId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("brandId").description("삭제할 브랜드 ID")
                        )
                ));
    }
}
