package cl.duocuc.huertohogar.backend.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Permitir solicitudes CORS desde http://localhost:5173 (frontend de Vite)
        registry.addMapping("/api/**")  // Aplica para todos los endpoints de la API
                .allowedOrigins("http://localhost:5173")  // Frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Métodos permitidos
                .allowedHeaders("Content-Type", "Authorization")  // Cabeceras permitidas
                .allowCredentials(true);  // Permitir credenciales si es necesario
    }
}

