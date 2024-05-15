package com.musinsa.shop.musinsashop.brand.infrastructure.persistence;

import com.musinsa.shop.musinsashop.brand.domain.Brand;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class JpaBrandRepositoryTest {

    @Autowired
    JpaBrandRepository jpaBrandRepository;

    @PersistenceContext
    private EntityManager em;

    private Brand A_브랜드;

    @BeforeEach
    void setUp(){
        A_브랜드 = new Brand("A");

        jpaBrandRepository.save(A_브랜드);

        em.flush();
        em.clear();
    }

    @Test
    void 브랜드명으로_조회한다(){
        //when
        final Optional<Brand> byBrandName = jpaBrandRepository.findByBrandName(A_브랜드.getBrandName());

        //then
        SoftAssertions.assertSoftly(softAssertions -> softAssertions.assertThat(byBrandName.get().getBrandName()).isEqualTo(A_브랜드.getBrandName()));
    }
}
