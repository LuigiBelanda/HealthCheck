package com.healtcheck.labeng.configs;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Health Check")
                        .description("API para gerenciamento de casos de doenças")
                        .version("1.0.0")
                        .contact(new Contact().name("Github do projeto").url("https://github.com/LuigiBelanda/HealthCheck/tree/master")))
                .externalDocs(new ExternalDocumentation()
                        .description("Endpoints de monitoramento (Spring Actuator)")
                        .url("/management"));
    }
}