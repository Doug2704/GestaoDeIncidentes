package br.edu.gti.gestao_incidentes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")        // Libera para qualquer origem
                        .allowedMethods("*")        // Libera todos os métodos (GET, POST, etc.)
                        .allowedHeaders("*")        // Libera todos os headers
                        .allowCredentials(false);   // Não envia cookies (requerido com "*")
            }
        };
    }
}
