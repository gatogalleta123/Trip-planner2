package cl.tripplanner.turismo.auth.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.tripplanner.common.security.JwtProperties;
import cl.tripplanner.common.security.JwtTokenProvider;
import cl.tripplanner.turismo.auth.client.UsuarioClient;
import cl.tripplanner.turismo.auth.dto.AuthResponse;
import cl.tripplanner.turismo.auth.dto.LoginRequest;
import cl.tripplanner.turismo.auth.dto.RegisterRequest;
import cl.tripplanner.turismo.auth.dto.UsuarioAuthResponse;
import cl.tripplanner.turismo.auth.dto.UsuarioInfoResponse;
import cl.tripplanner.turismo.auth.mapper.AuthMapper;
import cl.tripplanner.turismo.auth.model.AuditoriaLogin;
import cl.tripplanner.turismo.auth.model.MetodoAutenticacion;
import cl.tripplanner.turismo.auth.model.UsuarioAuth;
import cl.tripplanner.turismo.auth.repository.AuditoriaLoginRepository;
import cl.tripplanner.turismo.auth.repository.MetodoAutenticacionRepository;
import cl.tripplanner.turismo.auth.repository.UsuarioAuthRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthSecurityService {

    private final UsuarioAuthRepository usuarioAuthRepository;
    private final UsuarioClient usuarioClient;
    private final AuditoriaLoginRepository auditoriaLoginRepository;
    private final AuthMapper authMapper;
    private final MetodoAutenticacionRepository metodoAutenticacionRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;

    @Transactional
    public AuthResponse login(LoginRequest request) {

        UsuarioAuth usuario = usuarioAuthRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Credenciales inválidas"));

        if (!Boolean.TRUE.equals(usuario.getActivo())) {
            registrarLoginFallido(usuario, "IP_LOCAL");
            throw new RuntimeException("Cuenta desactivada");
        }

        if (!passwordEncoder.matches(
                request.getPassword(),
                usuario.getPasswordHash())) {

            registrarLoginFallido(usuario, "IP_LOCAL");
            throw new RuntimeException("Credenciales inválidas");
        }

        UsuarioInfoResponse info =
                usuarioClient.findUsuarioInfo(usuario.getEmail());

        String rol = mapRol(info.getRolId());

        registrarLoginExitoso(usuario, "IP_LOCAL");

        String token = jwtTokenProvider.generarToken(
                usuario.getEmail(),
                rol,
                info.getNombre()
        );

        return AuthResponse.builder()
                .email(usuario.getEmail())
                .nombre(info.getNombre())
                .rol(rol)
                .token(token)
                .expiresIn(jwtProperties.getExpirationMs())
                .message("Login exitoso")
                .build();
    }

    private String mapRol(Integer rolId) {
        return switch (rolId) {
            case 1 -> "ROLE_ADMIN";
            case 2 -> "ROLE_AGENTE";
            case 3 -> "ROLE_OPERADOR";
            case 4 -> "ROLE_INVITADO";
            default -> "ROLE_INVITADO";
        };
    }

    private void registrarLoginExitoso(UsuarioAuth usuario, String ipOrigen) {

        AuditoriaLogin log = AuditoriaLogin.builder()
                .usuario(usuario)
                .ipOrigen(ipOrigen)
                .exitoso(true)
                .build();

        auditoriaLoginRepository.save(log);
    }

    private void registrarLoginFallido(UsuarioAuth usuario, String ipOrigen) {

        AuditoriaLogin log = AuditoriaLogin.builder()
                .usuario(usuario)
                .ipOrigen(ipOrigen)
                .exitoso(false)
                .build();

        auditoriaLoginRepository.save(log);
    }

    //REGISTER
    @Transactional
    public UsuarioAuthResponse register(RegisterRequest request) {

        if (usuarioAuthRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con ese email");
        }

        MetodoAutenticacion metodo =
                metodoAutenticacionRepository
                        .findById(request.getMetodoAutenticacion())
                        .orElseThrow(() ->
                                new RuntimeException("Método de autenticación no existe"));

        UsuarioAuth usuario = new UsuarioAuth();
        usuario.setEmail(request.getEmail());
        usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        usuario.setMetodoAutenticacion(metodo);
        usuario.setActivo(true);
        usuario.setUltimoLogin(LocalDateTime.now());

        return authMapper.toResponse(
                usuarioAuthRepository.save(usuario));
    }
}
