package cl.duocuc.huertohogar.backend.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cl.duocuc.huertohogar.backend.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true) // Habilita @PreAuthorize y @Secured
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors() // Habilitar CORS globalmente
            .and()
            .csrf(AbstractHttpConfigurer::disable) // Desactiva CSRF para APIs 
            .authorizeHttpRequests(auth -> auth
                // Rutas públicas (para autenticación) solo de / api/v1/auth/
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("GET", "/api/products/**").permitAll()
                .requestMatchers("POST", "/api/products/**").authenticated()
                .requestMatchers("PUT", "/api/products/**").authenticated()
                .requestMatchers("DELETE", "/api/products/**").authenticated()
                .requestMatchers("OPTIONS", "/api/tickets/**").permitAll()
                .requestMatchers("/api/tickets/**").authenticated()
                .requestMatchers("OPTIONS", "/api/v1/admin/**").permitAll()
                .requestMatchers("/api/v1/admin/**").authenticated()
                
                // Otras rutas protegidas por roles globales (opcional, se usará @PreAuthorize en el controlador)
                .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // JWT sin sesión
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Añade el filtro JWT

        return http.build();
    }
}

