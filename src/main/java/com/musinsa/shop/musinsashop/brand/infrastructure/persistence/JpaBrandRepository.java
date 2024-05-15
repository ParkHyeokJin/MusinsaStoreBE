package com.musinsa.shop.musinsashop.brand.infrastructure.persistence;

import com.musinsa.shop.musinsashop.brand.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaBrandRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findByBrandName(String brandName);
}
