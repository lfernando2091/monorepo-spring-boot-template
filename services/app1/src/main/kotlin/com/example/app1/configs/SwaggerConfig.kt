package com.example.app1.configs

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import io.swagger.v3.oas.models.info.Info

@Configuration
class SwaggerConfig {

    @Bean
    open fun openApiDoc(): OpenAPI = OpenAPI()
        .info(
            Info()
                .description("Template Service")
                .title("Template")
                .version("0.0.1-SNAPSHOT")
                .contact(
                    Contact()
                        .email("help@template.com")
                        .name("Template Team")
                        .url("")
                )
        )
}