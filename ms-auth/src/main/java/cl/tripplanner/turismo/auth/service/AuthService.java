package cl.tripplanner.turismo.auth.service;

import java.time.LocalDateTime;
import java.util.List;

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
        usuario.setPasswordHash("Contraseña_totalmente_encriptada");
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
        UsuarioAuth usuario =
                getUsuarioByEmail(request.getEmail());

        if (!usuario.getActivo()) {
            registrarLoginFallido(
                    request.getEmail(),
                    "IP_del_usuario");

            throw new EntityNotFoundException(
                    "Usuario desactivado",
                    "email",
                    request.getEmail());
        }
        if (!usuario.getPasswordHash()
                .equals(request.getPasswordHash())) {

            registrarLoginFallido(
                    request.getEmail(),
                    "IP_del_usuario");

                    AuthResponse response = new AuthResponse();
                    response.setEmail(usuario.getEmail());
                    response.setMessage("Login fallido: Contraseña incorrecta :(");
                    return response;
        } else {
            registrarLoginExitoso(
                    request.getEmail(),
                    "IP_del_usuario");
                    AuthResponse response = new AuthResponse();
                    response.setEmail(usuario.getEmail());
                    response.setMessage("Login exitoso");
                    return response;
        }
    }

    @Transactional
    public void registrarLoginExitoso(String email,String ipOrigen) {

        UsuarioAuth usuario =
                getUsuarioByEmail(email);

        AuditoriaLogin auditoria =
                new AuditoriaLogin();

        auditoria.setUsuario(usuario);
        auditoria.setIpOrigen(ipOrigen);
        auditoria.setExitoso(true);

        auditoriaLoginRepository.save(auditoria);

        usuario.setUltimoLogin(
                java.time.LocalDateTime.now());

        usuarioAuthRepository.save(usuario);
    }

    @Transactional
    public void registrarLoginFallido(String email,String ipOrigen) {

        UsuarioAuth usuario =
                getUsuarioByEmail(email);

        AuditoriaLogin auditoria =
                new AuditoriaLogin();

        auditoria.setUsuario(usuario);
        auditoria.setIpOrigen(ipOrigen);
        auditoria.setExitoso(false);

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
