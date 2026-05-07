package com.luiscampillo19.eventify.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Swagger/OpenAPI con springdoc.
 * Documentación disponible en /swagger-ui.html
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI eventifyOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Eventify API")
                        .description("API REST para la gestión de eventos y lugares.\n\n" +
                                     "Módulo 6 – Spring Boot | TdeA")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("LuisCampillo19")
                                .url("https://github.com/LuisCampillo19")));
    }
}
