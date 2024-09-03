package com.example.dardar.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Pathologie API")
                        .description("Examen Blanc")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipe ASI II")
                                .email("chahnez.sardouk@esprit.tn")
                                .url("https://www.linkedin.com/in/**********/")));
    }
}
