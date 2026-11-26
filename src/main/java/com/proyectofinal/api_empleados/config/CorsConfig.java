package com.proyectofinal.api_empleados.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Política de CORS para toda la API, en un solo lugar.
// El front se abre con Live Server (puerto 5500) y la API corre en el 8080:
// son orígenes distintos, así que el navegador necesita este permiso.
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:5500", "http://localhost:5500")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
