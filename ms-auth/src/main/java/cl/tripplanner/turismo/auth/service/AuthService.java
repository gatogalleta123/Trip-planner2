package cl.tripplanner.turismo.auth.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.tripplanner.common.exception.DuplicateResourceException;
import cl.tripplanner.common.exception.EntityNotFoundException;
import cl.tripplanner.common.exception.ReferentialIntegrityException;
import cl.tripplanner.turismo.auth.client.UsuarioClient;
import cl.tripplanner.turismo.auth.dto.AuthResponse;
import cl.tripplanner.turismo.auth.dto.LoginRequest;
import cl.tripplanner.turismo.auth.dto.RegisterRequest;
import cl.tripplanner.turismo.auth.dto.UpdateRequest;
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

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final AuditoriaLoginRepository auditoriaLoginRepository;
    private final UsuarioAuthRepository usuarioAuthRepository;
    private final AuthMapper authMapper;
    private final UsuarioClient usuarioClient;
    private final MetodoAutenticacionRepository metodoAutenticacionRepository;


    public List<UsuarioAuthResponse> findAll() {
        return authMapper.toResponseList(usuarioAuthRepository.findAll());
    }

    public UsuarioAuthResponse findByEmail(String email) {
        return authMapper.toResponse(getUsuarioByEmail(email));
    }

    public List<MetodoAutenticacion> findAllMetodos() {
    return metodoAutenticacionRepository.findAll();
    }

    public UsuarioInfoResponse findUsuarioInfoByEmail(String email) {
        return usuarioClient.findUsuarioInfo(email);
    }

    public List<UsuarioInfoResponse> findAllUsuarioInfo() {
        return usuarioClient.findAllInfo();
    }

    @Transactional
    public UsuarioAuthResponse create(RegisterRequest request) {
        validateEmailUnico(request.getEmail());
        //Valida que el usuario exista en ms-usuarios para evitar inconsistencias, acá hay que capturar la excepcion cuando el usuario no exista pero no sé como
        usuarioClient.findUsuario(request.getEmail());
        MetodoAutenticacion metodo = getMetodoByCodigo(request.getMetodoAutenticacion());
        UsuarioAuth usuario = new UsuarioAuth();
        usuario.setPasswordHash(
            passwordEncoder.encode(
                request.getPassword()
            )
        );
        usuario.setMetodoAutenticacion(metodo);
        usuario.setActivo(true);
        usuario.setUltimoLogin(LocalDateTime.now());
        authMapper.updateEntity(request, usuario);
        return authMapper.toResponse(usuarioAuthRepository.save(usuario));
    }

    @Transactional
    public UsuarioAuthResponse update(String email, UpdateRequest request) {
        UsuarioAuth usuario = getUsuarioByEmail(email);
        authMapper.updateEntity(request, usuario);
        return authMapper.toResponse(usuarioAuthRepository.save(usuario));
    }

    @Transactional
    public void deleteByEmail(String email) {
        UsuarioAuth usuario = getUsuarioByEmail(email);
        if (!usuario.getAuditorias().isEmpty()) {
            throw new ReferentialIntegrityException("No se puede eliminar el usuario con email " , email , " porque tiene auditorias de login asociadas"
                    );
        }
        usuarioAuthRepository.delete(usuario);
    }


    //Auditoria

    @Transactional
    public AuthResponse login(LoginRequest request) {

        UsuarioAuth usuario = getUsuarioByEmail(request.getEmail());

        if (usuario.getActivo() == null || !usuario.getActivo()) {
            registrarLoginFallido(request.getEmail(), "IP_DEL_USUARIO");
            throw new EntityNotFoundException(
                    "Usuario desactivado",
                    "email",
                    request.getEmail()
            );
        }
            System.out.println("PASS INPUT: " + request.getPassword());

            System.out.println("HASH BD: " + usuario.getPasswordHash());

            System.out.println("MATCH: " + passwordEncoder.matches(request.getPassword(), usuario.getPasswordHash()));

        boolean passwordOk = passwordEncoder.matches(
                request.getPassword(),
                usuario.getPasswordHash()
        );

        if (!passwordOk) {
            registrarLoginFallido(request.getEmail(), "IP_DEL_USUARIO");

            AuthResponse response = new AuthResponse();
            response.setEmail(usuario.getEmail());
            response.setMessage("Login fallido: contraseña incorrecta");
            return response;
        }

        registrarLoginExitoso(request.getEmail(), "IP_DEL_USUARIO");

        AuthResponse response = new AuthResponse();
        response.setEmail(usuario.getEmail());
        response.setMessage("Login exitoso");
        return response;
    }

    @Transactional
    public void registrarLoginExitoso(String email, String ipOrigen) {

        UsuarioAuth usuario = getUsuarioByEmail(email);

        AuditoriaLogin auditoria = AuditoriaLogin.builder()
                .usuario(usuario)
                .ipOrigen(ipOrigen)
                .exitoso(true)
                .build();

        auditoriaLoginRepository.save(auditoria);

        usuario.setUltimoLogin(java.time.LocalDateTime.now());
        usuarioAuthRepository.save(usuario);
    }

    @Transactional
    public void registrarLoginFallido(String email, String ipOrigen) {

        UsuarioAuth usuario = getUsuarioByEmail(email);

        AuditoriaLogin auditoria = AuditoriaLogin.builder()
                .usuario(usuario)
                .ipOrigen(ipOrigen)
                .exitoso(false)
                .build();

        auditoriaLoginRepository.save(auditoria);
    }



    //Auxiliares
    private UsuarioAuth getUsuarioByEmail(String email) {
        return usuarioAuthRepository.findByEmail(email)
        .orElseThrow(() ->new EntityNotFoundException("Usuario no encontrado,", "email del usuario:", email));
    }

    private void validateEmailUnico(String email) {
        usuarioAuthRepository.findByEmail(email)
        .ifPresent(usuario -> {throw new DuplicateResourceException( "Ya existe un usuario con: ","email",email,"en usuarios"); });
    }

    private MetodoAutenticacion getMetodoByCodigo(String codigo) {
    return metodoAutenticacionRepository.findById(codigo)
            .orElseThrow(() ->
                    new EntityNotFoundException(
                            "Metodo de autenticacion no encontrado",
                            "codigo",
                            codigo));
    }

    public boolean existsByEmail(String email) {
    return usuarioAuthRepository.existsByEmail(email);
    }

}
