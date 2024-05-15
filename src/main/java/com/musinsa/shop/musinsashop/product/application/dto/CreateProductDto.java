package com.musinsa.shop.musinsashop.product.application.dto;

import com.musinsa.shop.musinsashop.brand.application.dto.ReadBrandDto;
import com.musinsa.shop.musinsashop.brand.domain.Brand;
import com.musinsa.shop.musinsashop.category.application.dto.ReadCategoryDto;
import com.musinsa.shop.musinsashop.category.domain.Category;
import com.musinsa.shop.musinsashop.product.domain.Product;

public record CreateProductDto(String productName,
                               long productPrice,
                               long categoryId,
                               long brandId) {
    public Product toEntity(final String productName, long productPrice, Category category, Brand brand){
        return new Product(productName, productPrice, brand, category);
    }

    public static CreateProductDto of (String productName, long productPrice, long categoryId, long brandId){
        return new CreateProductDto(
                productName, productPrice, categoryId, brandId);
    }
}
