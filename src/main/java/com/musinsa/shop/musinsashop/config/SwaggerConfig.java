package com.musinsa.shop.musinsashop.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {
    @Bean
    public OpenAPI api(){
        Info info = new Info().title("Musinsa Shop B/E")
                .version("v1.0")
                .description("Musinsa Shop B/E");
        return new OpenAPI().components(new Components()).info(info);
    }
}
