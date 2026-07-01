package cl.tripplanner.turismo.usuarios.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import cl.tripplanner.common.exception.DuplicateResourceException;
import org.springframework.stereotype.Service;

import cl.tripplanner.common.event.UsuarioCreatedEvent;
import cl.tripplanner.common.event.UsuarioDeletedEvent;
import cl.tripplanner.common.event.UsuarioUpdatedEvent;
import cl.tripplanner.common.exception.EntityNotFoundException;
import cl.tripplanner.common.exception.ReferentialIntegrityException;
import cl.tripplanner.turismo.usuarios.client.AuthClient;
import cl.tripplanner.turismo.usuarios.dto.OrganizacionRequest;
import cl.tripplanner.turismo.usuarios.dto.OrganizacionResponse;
import cl.tripplanner.turismo.usuarios.dto.RolRequest;
import cl.tripplanner.turismo.usuarios.dto.RolResponse;
import cl.tripplanner.turismo.usuarios.dto.UsuarioRequest;
import cl.tripplanner.turismo.usuarios.dto.UsuarioResponse;
import cl.tripplanner.turismo.usuarios.dto.UsuarioUpdateRequest;
import cl.tripplanner.turismo.usuarios.event.UsuarioEventProducer;
import cl.tripplanner.turismo.usuarios.mapper.OrganizacionMapper;
import cl.tripplanner.turismo.usuarios.mapper.RolMapper;
import cl.tripplanner.turismo.usuarios.mapper.UsuarioMapper;
import cl.tripplanner.turismo.usuarios.model.Organizacion;
import cl.tripplanner.turismo.usuarios.model.Rol;
import cl.tripplanner.turismo.usuarios.model.Usuario;
import cl.tripplanner.turismo.usuarios.repository.OrganizacionRepository;
import cl.tripplanner.turismo.usuarios.repository.RolRepository;
import cl.tripplanner.turismo.usuarios.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final AuthClient authClient;
    private final RolRepository rolRepository;
    private final OrganizacionRepository organizacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioEventProducer usuarioEventProducer;
    private final OrganizacionMapper organizacionMapper;
    private final RolMapper rolMapper;

    public List<UsuarioResponse> findAll() {
        return usuarioMapper.toResponseList(usuarioRepository.findAll());
    }

    public UsuarioResponse findByEmail(String email) {
        return usuarioMapper.toResponse(getUsuarioByEmail(email));
    }

//------------------
//CREATE
//------------------
    @Transactional
    public UsuarioResponse create(UsuarioRequest request){
        validateEmailUnico(request.getEmail());

        Rol rol = rolRepository.findById(request.getRolId())
                .orElseThrow(() -> new EntityNotFoundException("Rol","id" ,request.getRolId()));
        
        Organizacion organizacion = null;
        if (request.getOrganizacionId() != null) {
            organizacion = organizacionRepository.findById(request.getOrganizacionId())
                    .orElseThrow(() -> new EntityNotFoundException("Organizacion","id" ,request.getOrganizacionId()));
        }
        Usuario usuario = new Usuario();
        usuario.setRol(rol);
        usuario.setOrganizacion(organizacion);
        usuario.setFechaRegistro(LocalDate.now());
        usuario.setActivo(true);
        usuarioMapper.updateEntity(request, usuario);
        usuarioRepository.save(usuario);
        UsuarioCreatedEvent event = new UsuarioCreatedEvent(usuario.getEmail(), usuario.getNombre(), usuario.getActivo(), LocalDateTime.now());
        usuarioEventProducer.sendCreated(event);
        return usuarioMapper.toResponse(usuario);
    }

    private Usuario getUsuarioByEmail(String email){
        return usuarioRepository.findByEmail(email).
        orElseThrow(()-> new cl.tripplanner.common.exception.EntityNotFoundException("Usuario", "email", email));
    }

    private void validateEmailUnico(String email) {
        usuarioRepository.findByEmail(email).ifPresent(l -> { throw new DuplicateResourceException("Usuario", "email", email, "email duplicado" ); });
    }

    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

//------------------
//UPDATE
//------------------
    @Transactional
    public UsuarioResponse update(String email, UsuarioUpdateRequest request) {
        Usuario usuario = getUsuarioByEmail(email);
        usuarioMapper.updateEntity(request, usuario);
        Rol rol = rolRepository.findById(request.getRolId())
                .orElseThrow(() -> new EntityNotFoundException("Rol","id" ,request.getRolId()));
        usuario.setRol(rol);
        Organizacion organizacion = null;
        if (request.getOrganizacionId() != null) {
            organizacion = organizacionRepository.findById(request.getOrganizacionId())
                    .orElseThrow(() -> new EntityNotFoundException("Organizacion","id" ,request.getOrganizacionId()));
        }
        usuario.setOrganizacion(organizacion);
        usuarioRepository.save(usuario);
        UsuarioUpdatedEvent event = new UsuarioUpdatedEvent(usuario.getEmail(), usuario.getNombre(), usuario.getActivo(), LocalDateTime.now());
        usuarioEventProducer.sendUpdated(event);
        return usuarioMapper.toResponse(usuario);
    }

//------------------
//DELETE
//------------------
    @Transactional
    public void deleteByEmail(String email) {
        Usuario usuario = getUsuarioByEmail(email);
        //Integridad referencial en Auth
        List<String> tablasAsociadas = new ArrayList<>();
        if (authClient.existsByEmail(email)) {
            tablasAsociadas.add("Credenciales en Auth");
        }
        if (!tablasAsociadas.isEmpty()) {
            throw new ReferentialIntegrityException("Usuario", email,String.join(", ", tablasAsociadas));
        }

        usuarioRepository.delete(usuario);
        UsuarioDeletedEvent event = new UsuarioDeletedEvent(email, LocalDateTime.now());
        usuarioEventProducer.sendDeleted(event);
    }

//------------------
//ACTIVAR/DESACTIVAR
//------------------
@Transactional
public UsuarioResponse activar(String email) {
    Usuario usuario = getUsuarioByEmail(email);
    usuario.setActivo(true);
    // Se guarda el cambio y se mapea a la respuesta DTO 
    return usuarioMapper.toResponse(usuarioRepository.save(usuario));
}

@Transactional
public UsuarioResponse desactivar(String email) {
    Usuario usuario = getUsuarioByEmail(email);
    usuario.setActivo(false);
    return usuarioMapper.toResponse(usuarioRepository.save(usuario));
}

//ORGANIZACION
    public OrganizacionResponse findOrganizacionById(Integer id) {
    Organizacion organizacion = organizacionRepository.findById(id)
            .orElseThrow(() ->
                    new EntityNotFoundException(
                            "Organizacion",
                            "id",
                            id));

    return organizacionMapper.toResponse(organizacion);
    }

    public List<OrganizacionResponse> findAllOrganizaciones() {
    return organizacionMapper.toOrganizacionResponseList(
            organizacionRepository.findAll());
    }

    @Transactional
    public OrganizacionResponse createOrganizacion(
            OrganizacionRequest request) {

        Organizacion organizacion =
                organizacionMapper.toEntity(request);

        organizacionRepository.save(organizacion);

        return organizacionMapper.toResponse(organizacion);
    }

    @Transactional
    public OrganizacionResponse updateOrganizacion(
            Integer id,
            OrganizacionRequest request) {

        Organizacion organizacion =
                organizacionRepository.findById(id)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Organizacion",
                                        "id",
                                        id));

        organizacionMapper.updateEntity(
                request,
                organizacion);

        organizacionRepository.save(organizacion);

        return organizacionMapper.toResponse(organizacion);
    }

    @Transactional
    public void deleteOrganizacion(Integer id) {

        Organizacion organizacion =
                organizacionRepository.findById(id)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Organizacion",
                                        "id",
                                        id));

        List<String> tablasAsociadas =
                new ArrayList<>();

        if (usuarioRepository.existsByOrganizacionId(id)) {
            tablasAsociadas.add("Usuarios");
        }

        if (!tablasAsociadas.isEmpty()) {
            throw new ReferentialIntegrityException(
                    "Organizacion",
                    organizacion.getNombre(),
                    String.join(", ", tablasAsociadas));
        }

        organizacionRepository.delete(organizacion);
    }

    public boolean existsOrganizacionById(Integer id) {
    return organizacionRepository.existsById(id);
    }

    //ROL
    public RolResponse findRolById(Integer id) {

    Rol rol = rolRepository.findById(id)
            .orElseThrow(() ->
                    new EntityNotFoundException(
                            "Rol",
                            "id",
                            id));

    return rolMapper.toResponse(rol);
    }

    public List<RolResponse> findAllRoles() {
    return rolMapper.toRolResponseList(
            rolRepository.findAll());
    }

    @Transactional
    public RolResponse createRol(
            RolRequest request) {

        Rol rol =
                rolMapper.toEntity(request);

        rolRepository.save(rol);

        return rolMapper.toResponse(rol);
    }

    @Transactional
    public RolResponse updateRol(
        Integer id,
        RolRequest request) {

    Rol rol = rolRepository.findById(id)
            .orElseThrow(() ->
                    new EntityNotFoundException(
                            "Rol",
                            "id",
                            id));

    rolMapper.updateEntity(
            request,
            rol);

    rolRepository.save(rol);

    return rolMapper.toResponse(rol);
    }

    @Transactional
    public void deleteRol(Integer id) {

    Rol rol = rolRepository.findById(id)
            .orElseThrow(() ->
                    new EntityNotFoundException(
                            "Rol",
                            "id",
                            id));

    List<String> tablasAsociadas =
            new ArrayList<>();

    if (usuarioRepository.existsByRolId(id)) {
        tablasAsociadas.add("Usuarios");
    }

    if (!tablasAsociadas.isEmpty()) {
        throw new ReferentialIntegrityException(
                "Rol",
                rol.getNombre(),
                String.join(", ", tablasAsociadas));
    }

    rolRepository.delete(rol);
    }

    public boolean existsRolById(Integer id) {
    return rolRepository.existsById(id);
    }
}
