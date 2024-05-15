package com.musinsa.shop.musinsashop.brand.infrastructure.persistence;

import com.musinsa.shop.musinsashop.brand.domain.Brand;
import com.musinsa.shop.musinsashop.brand.domain.repository.BrandRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class BrandRepositoryImplTest {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private JpaBrandRepository jpaBrandRepository;

    BrandRepository brandRepository;

    private Brand A_브랜드;

    @BeforeEach
    void setUp(){
        A_브랜드 = new Brand("A");
        jpaBrandRepository.save(A_브랜드);
        em.flush();
        em.clear();

        brandRepository = new BrandRepositoryImpl(jpaBrandRepository);
    }

    @Test
    void 브랜드를_생성할수_있다(){
        Brand brand = brandRepository.saveBrand(new Brand("B"));

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(brand.getBrandName()).isEqualTo("B");
        });
    }
}
