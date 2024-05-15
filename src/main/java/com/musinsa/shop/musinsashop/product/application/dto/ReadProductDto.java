package com.musinsa.shop.musinsashop.product.application.dto;

import com.musinsa.shop.musinsashop.brand.application.dto.ReadBrandDto;
import com.musinsa.shop.musinsashop.category.application.dto.ReadCategoryDto;
import com.musinsa.shop.musinsashop.product.domain.Product;

public record ReadProductDto (long id,
                              String productName,
                              long productPrice,
                              ReadCategoryDto categoryDto,
                              ReadBrandDto brandDto)
{
    public static ReadProductDto from(final Product product){
        return new ReadProductDto(
                product.getId(),
                product.getProductName(),
                product.getProductPrice(),
                ReadCategoryDto.from(product.getCategory()),
                ReadBrandDto.from(product.getBrand())
        );
    }
}
