package com.musinsa.shop.musinsashop.category.presentation;

import com.musinsa.shop.musinsashop.brand.application.exception.AlreadyBrandException;
import com.musinsa.shop.musinsashop.category.application.dto.CreateCategoryDto;
import com.musinsa.shop.musinsashop.category.application.dto.ReadCategoryDto;
import com.musinsa.shop.musinsashop.category.application.exception.CategoryNotFoundException;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest extends IntegrationTest {
    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
                .alwaysDo(print())
                .alwaysDo(restDocs)
                .build();
    }

    @Test
    void 카테고리를_생성할수_있다() throws Exception {
        //given
        final CreateCategoryDto createCategoryDto = new CreateCategoryDto("TEST");
        final ReadCategoryDto readCategoryDto = new ReadCategoryDto(1L, "TEST");
        given(categoryService.saveCategory(createCategoryDto)).willReturn(readCategoryDto);

        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.post("/category")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createCategoryDto)))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id", is(readCategoryDto.id()), Long.class),
                        jsonPath("$.categoryName", is(readCategoryDto.categoryName()))
                )
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
                                        fieldWithPath("categoryName").type(JsonFieldType.STRING).description("카테고리 명")
                                )
                        )
                );
    }

    @Test
    void 카테고리_생성시_중복인경우_400_오류를_반환한다() throws Exception {
        final CreateCategoryDto createCategoryDto = new CreateCategoryDto("TEST");
        final AlreadyBrandException alreadyBrandException = new AlreadyBrandException("중복된 카테고리 입니다.");

        given(categoryService.saveCategory(createCategoryDto)).willThrow(alreadyBrandException);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/category")
                        .content(objectMapper.writeValueAsString(createCategoryDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.message", is(alreadyBrandException.getMessage()))
                );
    }

    @Test
    void 카테고리를_삭제할수_있다() throws Exception {
        willDoNothing().given(categoryService).deleteCategory(anyLong());

        mockMvc.perform(RestDocumentationRequestBuilders.delete("/category/{categoryId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk())
                .andDo(
                        restDocs.document(
                                pathParameters(
                                        parameterWithName("categoryId").description("삭제할 카테고리 ID")
                                )
                        ));
    }

    @Test
    void 카테고리명을_수정할수_있다() throws Exception{
        Map<String, Object> updateRequest = new HashMap<>();
        updateRequest.put("oldCategoryName", "OLD_CATEGORY");
        updateRequest.put("newCategoryName", "NEW_CATEGORY");

        final ReadCategoryDto readCategoryDto = new ReadCategoryDto(1L, "NEW_CATEGORY");
        given(categoryService.updateCategory("OLD_CATEGORY", "NEW_CATEGORY")).willReturn(readCategoryDto);

        mockMvc.perform(RestDocumentationRequestBuilders.put("/category")
                        .content(objectMapper.writeValueAsString(updateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id", is(readCategoryDto.id()), Long.class),
                        jsonPath("$.categoryName", is(readCategoryDto.categoryName()))
                )
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
                                        fieldWithPath("categoryName").type(JsonFieldType.STRING).description("카테고리 명")
                                )
                        )
                );
    }

    @Test
    void 카테고리_수정시_카테고리가_없는경우_404_오류를_반환한다() throws Exception{
        Map<String, Object> updateRequest = new HashMap<>();
        updateRequest.put("oldCategoryName", "OLD_CATEGORY");
        updateRequest.put("newCategoryName", "NEW_CATEGORY");

        final CategoryNotFoundException categoryNotFoundException = new CategoryNotFoundException("등록된 카테고리가 없습니다.");
        given(categoryService.updateCategory("OLD_CATEGORY", "NEW_CATEGORY")).willThrow(categoryNotFoundException);

        mockMvc.perform(RestDocumentationRequestBuilders.put("/category")
                        .content(objectMapper.writeValueAsString(updateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message", is(categoryNotFoundException.getMessage()))
                );
    }

    @Test
    void 카테고리명으로_조회할수_있다() throws Exception {
        //given
        final ReadCategoryDto readCategoryDto = new ReadCategoryDto(1L, "CATEGORY");
        given(categoryService.selectCategoryByName(anyString())).willReturn(readCategoryDto);

        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/category/findCategoryName/CATEGORY")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id", is(readCategoryDto.id()), Long.class),
                        jsonPath("$.categoryName", is(readCategoryDto.categoryName()))
                )
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
                                        fieldWithPath("categoryName").type(JsonFieldType.STRING).description("카테고리 명")
                                )
                        )
                );
    }

    @Test
    void 카테고리아이디로_조회할수_있다() throws Exception {
        final ReadCategoryDto readCategoryDto = new ReadCategoryDto(1L, "CATEGORY");
        given(categoryService.selectCategory(anyLong())).willReturn(readCategoryDto);

        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/category/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id", is(readCategoryDto.id()), Long.class),
                        jsonPath("$.categoryName", is(readCategoryDto.categoryName()))
                )
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
                                        fieldWithPath("categoryName").type(JsonFieldType.STRING).description("카테고리 명")
                                )
                        )
                );
    }

    @Test
    void 카테고리_리스트를_조회할수_있다() throws Exception {
        //given
        final ReadCategoryDto readCategoryDto1 = new ReadCategoryDto(1L, "CATEGORY");
        final ReadCategoryDto readCategoryDto2 = new ReadCategoryDto(2L, "CATEGORY");
        final ReadCategoryDto readCategoryDto3 = new ReadCategoryDto(3L, "CATEGORY");
        given(categoryService.selectCategories(0, 10)).willReturn(List.of(readCategoryDto1, readCategoryDto2, readCategoryDto3));

        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/category/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.[0].id", is(readCategoryDto1.id()), Long.class),
                        jsonPath("$.[0].categoryName", is(readCategoryDto1.categoryName())),
                        jsonPath("$.[1].id", is(readCategoryDto2.id()), Long.class),
                        jsonPath("$.[1].categoryName", is(readCategoryDto2.categoryName())),
                        jsonPath("$.[2].id", is(readCategoryDto3.id()), Long.class),
                        jsonPath("$.[2].categoryName", is(readCategoryDto3.categoryName()))
                )
                .andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("카테고리 ID"),
                                        fieldWithPath("[].categoryName").type(JsonFieldType.STRING).description("카테고리 명")
                                )
                        )
                );
    }
}
