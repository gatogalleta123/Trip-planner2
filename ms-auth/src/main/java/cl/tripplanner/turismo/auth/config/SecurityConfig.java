package cl.tripplanner.turismo.auth.config;

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

import cl.tripplanner.common.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http

            // API REST + JWT => no CSRF
            .csrf(csrf -> csrf.disable())

            // JWT => sin sesiones
            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            .authorizeHttpRequests(auth -> auth

                // Swagger
                .requestMatchers(
                    "/v3/api-docs/**",
                    "/v3/api-docs.yaml",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/swagger-resources/**",
                    "/webjars/**",
                    "/favicon.ico"
                ).permitAll()

                // Actuator
                .requestMatchers("/actuator/**")
                .permitAll()

                // Login público
                .requestMatchers(
                    HttpMethod.POST,
                    "/api/v1/auth/login"
                ).permitAll()

                .requestMatchers(
                    HttpMethod.POST,
                    "/api/v1/auth"
                ).permitAll()
                
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/v1/auth/exists/**"
                ).permitAll()
                // Verificar existencia
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/v1/auth/exists/**"
                ).permitAll()

                // Todo lo demás requiere token
                .anyRequest()
                .authenticated()
            )

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );
            //.anyRequest().permitAll()

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
