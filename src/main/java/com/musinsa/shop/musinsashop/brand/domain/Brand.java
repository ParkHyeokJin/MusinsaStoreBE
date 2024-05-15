package com.musinsa.shop.musinsashop.brand.domain;

import com.musinsa.shop.musinsashop.product.domain.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.util.Set;

@Entity
@Getter
@Table(name = "brand")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String brandName;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    Set<Product> productSet;

    public Brand(final String brandName){
        this.brandName = brandName;
    }

    public void updateBrandName(final String brandName){
        if(!ObjectUtils.isEmpty(brandName)){
            this.brandName = brandName;
        }
    }
}
