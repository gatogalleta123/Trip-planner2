package cl.tripplanner.turismo.notificaciones.config;

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
            .csrf(csrf -> csrf.disable()) // Stateless: no requiere CSRF [5]
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Sin estado [5, 6]
            )
            .authorizeHttpRequests(auth -> auth
                // Rutas públicas (monitoreo) [7]
                .requestMatchers("/actuator/**").permitAll()
                
                // Configuración de roles según la matriz de permisos [7, 8]
                .requestMatchers("/api/v1/usuarios-proyeccion/**").hasAnyRole("ADMIN", "OPERADOR")
                .requestMatchers(HttpMethod.GET, "/notificaciones/**").hasAnyRole("ADMIN", "OPERADOR", "AGENTE")
                .requestMatchers("/notificaciones/**").hasAnyRole("ADMIN", "OPERADOR")
                
                .anyRequest().authenticated()
            )
            // Filtro JWT antes del filtro de autenticación estándar [5, 9]
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}