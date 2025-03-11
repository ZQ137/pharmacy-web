package com.example.pharmacy.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "药品信息管理分析系统 API",
                version = "1.0",
                description = "这是一个 Spring Boot 药品信息管理分析的 API 文档",
                contact = @Contact(name = "李旭",email = "test@qq.com")
        ),
        security = @SecurityRequirement(name="BearerAuth")
)
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("BearerAuth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                ))
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("药品信息管理分析系统 API")
                        .version("1.0")
                        .description("这是一个 Spring Boot 药品信息管理分析的 API 文档")
                        .contact(new io.swagger.v3.oas.models.info.Contact().name("李旭").email("test@qq.com"))
                );
    }
}
