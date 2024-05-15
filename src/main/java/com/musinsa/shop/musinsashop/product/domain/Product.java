package com.musinsa.shop.musinsashop.product.domain;

import com.musinsa.shop.musinsashop.brand.domain.Brand;
import com.musinsa.shop.musinsashop.category.domain.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    private String productName;

    private long productPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "brand_id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public Product(final String productName, final long productPrice, final Brand brand, final Category category){
        this.productName = productName;
        this.productPrice = productPrice;
        this.brand = brand;
        this.category = category;
    }
}
