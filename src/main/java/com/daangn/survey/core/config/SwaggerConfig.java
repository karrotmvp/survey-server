package com.daangn.survey.core.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("DangGeun Survey API")
                        .description("DangGeun Survey Application")
                        .version("v0.0.1"))
                .externalDocs(new ExternalDocumentation()
                        .description("DangGeun Survey Wiki Documentation")
                        .url("https://github.com/karrotmvp/survey-server"));
    }
}
