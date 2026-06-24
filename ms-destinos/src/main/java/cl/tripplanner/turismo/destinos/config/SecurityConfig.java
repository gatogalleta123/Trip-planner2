package cl.tripplanner.turismo.destinos.config;

import cl.tripplanner.common.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Stateless: no requiere CSRF [2]
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // [1, 9]
            )
            .authorizeHttpRequests(auth -> auth
                // Rutas públicas: monitoreo y Swagger [2, 10, 11]
                .requestMatchers("/actuator/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                
                // Lectura permitida para todos los usuarios autenticados
                .requestMatchers(HttpMethod.GET, "/api/destinos/**", "/api/paises/**", "/api/atracciones/**")
                    .hasAnyRole("ADMIN", "OPERADOR", "AGENTE", "INVITADO")
                
                // Escritura restringida a ADMIN y OPERADOR [12, 13]
                .requestMatchers("/api/destinos/**", "/api/paises/**", "/api/atracciones/**")
                    .hasAnyRole("ADMIN", "OPERADOR")
                
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // [2, 9]

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // [2, 9]
    }
}