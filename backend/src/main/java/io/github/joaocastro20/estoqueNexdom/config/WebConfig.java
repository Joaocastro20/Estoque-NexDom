package io.github.joaocastro20.estoqueNexdom.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // REMOVE esta configuração se quiser "remover o CORS"
                .allowedOrigins("*")
                .allowedMethods("*");
    }
}
