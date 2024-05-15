package com.musinsa.shop.musinsashop.category.domain;

import com.musinsa.shop.musinsashop.category.application.dto.ReadCategoryDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

@Entity
@Getter
@Table(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String categoryName;

    public Category(final String categoryName){
        this.categoryName = categoryName;
    }

    public void updateCategoryName(final String categoryName){
        if(!ObjectUtils.isEmpty(categoryName)){
            this.categoryName = categoryName;
        }
    }
}
