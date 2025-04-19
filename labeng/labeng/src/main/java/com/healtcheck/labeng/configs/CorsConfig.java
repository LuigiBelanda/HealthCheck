package com.healtcheck.labeng.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Indica que esta é uma classe de configuração do Spring
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    // Este método sobrescreve a configuração padrão de CORS do Spring MVC
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica esta configuração para todas as rotas/endpoints
                .allowedOrigins("*") // Permite requisições de qualquer origem (*). CUIDADO: em produção, substitua por domínios específicos
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permite esses métodos HTTP
                .allowedHeaders("*") // Permite qualquer cabeçalho nas requisições
                .allowCredentials(false) // Não permite envio de cookies/autenticação junto com a requisição
                .maxAge(3600); // Tempo em segundos que o navegador pode guardar a resposta do CORS (1 hora)
    }

    // Este método cria e configura um CorsFilter como um Bean do Spring
    @Bean
    public CorsFilter corsFilter() {
        // Cria uma fonte de configuração baseada em URLs
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Cria uma nova configuração de CORS
        CorsConfiguration config = new CorsConfiguration();

        config.addAllowedOrigin("*"); // Permite qualquer origem. Em produção, substitua pelo domínio do frontend
        config.addAllowedHeader("*"); // Permite qualquer cabeçalho HTTP
        config.addAllowedMethod("*"); // Permite qualquer método HTTP (GET, POST, etc.)
        config.setAllowCredentials(false); // Não permite envio de cookies/autenticação

        // Registra essa configuração para todas as rotas da API
        source.registerCorsConfiguration("/**", config);

        // Retorna um filtro com essa configuração
        return new CorsFilter(source);
    }
}
