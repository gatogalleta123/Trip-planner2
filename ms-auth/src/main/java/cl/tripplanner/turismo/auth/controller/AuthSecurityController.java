package cl.tripplanner.turismo.auth.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.tripplanner.common.security.JwtTokenProvider;
import cl.tripplanner.common.security.TokenBlacklistService;
import cl.tripplanner.turismo.auth.dto.AuthResponse;
import cl.tripplanner.turismo.auth.dto.LoginRequest;
import cl.tripplanner.turismo.auth.dto.RegisterRequest;
import cl.tripplanner.turismo.auth.dto.UsuarioAuthResponse;
import cl.tripplanner.turismo.auth.service.AuthSecurityService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticación", description = "Endpoints para el inicio de sesión, registro y cierre de sesión") // [8, 9]
public class AuthSecurityController {

    private final AuthSecurityService authSecurityService;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlacklistService tokenBlacklistService;

   @Operation(summary = "Iniciar sesión", description = "Valida credenciales y retorna un token JWT") // [8]
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login exitoso", 
            content = @Content(schema = @Schema(implementation = AuthResponse.class))),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas o cuenta desactivada", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @org.springframework.web.bind.annotation.RequestBody LoginRequest request) {
        return ResponseEntity.ok(authSecurityService.login(request));
    }

    @Operation(summary = "Registrar usuario", description = "Crea una nueva cuenta de usuario en el sistema")
    @ApiResponses(value = {
        @ApiResponse(
        responseCode = "201",
        description = "Usuario registrado correctamente",
        content = @Content(schema = @Schema(implementation = UsuarioAuthResponse.class))),
        @ApiResponse(responseCode = "409", description = "El email ya se encuentra en uso")
    })
    @PostMapping("/register")
    public ResponseEntity<UsuarioAuthResponse> register(@Valid @org.springframework.web.bind.annotation.RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authSecurityService.register(request));
    }

    @Operation(
        summary = "Cerrar sesión",
        description = "Invalida el token JWT actual agregándolo a la blacklist"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Sesión cerrada exitosamente"
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Token inválido o expirado"
        )
    })
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(
            @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.substring(7);

        Date expiration = jwtTokenProvider.getExpirationFromToken(token);

        tokenBlacklistService.addToBlacklist(token, expiration);

        String email = jwtTokenProvider.getEmailFromToken(token);

        log.info("Logout exitoso para: {}", email);

        return ResponseEntity.ok(
                Map.of("message", "Sesión cerrada exitosamente"));
    }

}