package com.groupeisi.HelloSpring.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API HelloSpring - Gestion Étudiants, Entreprises et Stages")
                        .version("1.0.0")
                        .description("API REST pour la gestion complète (CRUD) des étudiants, des entreprises et des stages.")
                        .contact(new Contact()
                                .name("Groupe ISI")
                                .email("contact@groupeisi.com")));
    }
}
