package com.musinsa.shop.musinsashop.product.infrastructure.persistence;

import com.musinsa.shop.musinsashop.brand.domain.Brand;
import com.musinsa.shop.musinsashop.category.domain.Category;
import com.musinsa.shop.musinsashop.product.domain.Product;
import com.musinsa.shop.musinsashop.recommendation.application.dto.ReadCategoriesMinimumPriceProductsDto;
import com.musinsa.shop.musinsashop.recommendation.application.dto.ReadTotalMinimumPriceBrandDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JpaProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p JOIN FETCH p.brand JOIN FETCH p.category WHERE p.id = :id ")
    Optional<Product> findByProductId(long id);

    @Query("SELECT p from Product p JOIN FETCH p.brand JOIN FETCH p.category WHERE p.productName = :productName ")
    Optional<Product> findByProductName(String productName);

    Optional<Product> findByBrandAndProductName(Brand brand, String productName);

    Optional<Product> findByCategoryAndProductName(Category category, String productName);

    Optional<Product> findByCategoryAndBrandAndProductName(Category category, Brand brand, String productName);

    @Query("SELECT p FROM  Product p JOIN FETCH p.category WHERE p.brand = :brand ")
    Page<Product> findByBrand(Brand brand, Pageable pageable);

    @Query("SELECT p FROM Product p JOIN FETCH p.brand WHERE p.category = :category ")
    Page<Product> findByCategory(Category category, Pageable pageable);

    @Query("SELECT p " +
            "FROM Product p " +
            "JOIN FETCH p.category c " +
            "JOIN FETCH p.brand b " +
            "WHERE (p.category.id, p.productPrice) IN (SELECT p2.category.id, MIN(p2.productPrice) FROM Product p2 GROUP BY p2.category) "
    )
    List<Product> findCategoryMinimumPriceProducts();

    @Query("SELECT p FROM Product p JOIN FETCH p.category c where p.brand = :brand ")
    List<Product> findByTotalMinimumPriceBrandProducts(Brand brand);

    @Query("SELECT p FROM Product p JOIN FETCH p.category c JOIN FETCH p.brand b where p.category = :category order by p.productPrice limit 1 ")
    Optional<Product> findMinimumPriceProductByCategory(Category category);

    @Query("SELECT p FROM Product p JOIN FETCH p.category c JOIN FETCH p.brand b where p.category = :category order by p.productPrice desc limit 1 ")
    Optional<Product> findMaximumPriceProductByCategory(Category category);

    @Query("SELECT p.brand FROM Product p GROUP BY p.brand ORDER BY SUM(p.productPrice) limit 1 ")
    Optional<Brand> findTotalMinimumPriceBrand();
}
