package com.musinsa.shop.musinsashop.recommendation.application.dto;

public record ReadMinimumCategoryPrice (String category, long price){
    public static ReadMinimumCategoryPrice of(final String categoryName, final long productPrice){
        return new ReadMinimumCategoryPrice(categoryName, productPrice);
    }
}
