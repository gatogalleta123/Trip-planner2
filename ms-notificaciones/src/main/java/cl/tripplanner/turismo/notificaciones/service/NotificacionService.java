package cl.tripplanner.turismo.notificaciones.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.tripplanner.turismo.notificaciones.dto.LogNotificacionResponse;
import cl.tripplanner.turismo.notificaciones.dto.NotificacionRequest;
import cl.tripplanner.turismo.notificaciones.dto.NotificacionResponse;
import cl.tripplanner.turismo.notificaciones.mapper.NotificacionMapper;
import cl.tripplanner.turismo.notificaciones.model.LogNotificacion;
import cl.tripplanner.turismo.notificaciones.model.Notificacion;
import cl.tripplanner.turismo.notificaciones.model.UsuarioProyeccion;
import cl.tripplanner.turismo.notificaciones.repository.LogNotificacionRepository;
import cl.tripplanner.turismo.notificaciones.repository.NotificacionRepository;
import cl.tripplanner.turismo.notificaciones.repository.UsuarioProyeccionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificacionService {
    private final UsuarioProyeccionRepository usuarioProyeccionRepository;
    private final NotificacionRepository notificacionRepository;
    private final LogNotificacionRepository logNotificacionRepository;
    private final NotificacionMapper notificacionMapper;
    public List<NotificacionResponse> findAll() {
        return notificacionMapper.toResponseList(notificacionRepository.findAll());
    }

    public NotificacionResponse findByCodigo(String codigo) {
        return notificacionMapper.toResponse(
                getNotificacionByCodigo(codigo)
        );
    }

    public List<NotificacionResponse> findByCanal(String canal) {
        return notificacionMapper.toResponseList(notificacionRepository.findByCanal(canal)
        );
    }

    public List<NotificacionResponse> findByEnviada(Boolean enviada) {
        return notificacionMapper.toResponseList(
                notificacionRepository.findByEnviada(enviada)
        );
    }

    @Transactional
    public NotificacionResponse create(NotificacionRequest request) {
        validateCodigoUnico(request.getCodigo());
        UsuarioProyeccion usuario = usuarioProyeccionRepository
                .findByEmail(request.getUsuarioEmail())
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));
        Notificacion notificacion = new Notificacion();
        notificacionMapper.updateEntity(request, notificacion);
        notificacion.setUsuario(usuario);
        notificacion.setEnviada(false);
        Notificacion saved = notificacionRepository.save(notificacion);
        return notificacionMapper.toResponse(saved);
    }

    @Transactional
    public NotificacionResponse update(String codigo,
                                       NotificacionRequest request) {
        Notificacion notificacion = getNotificacionByCodigo(codigo);
        if (!notificacion.getCodigo()
                .equalsIgnoreCase(request.getCodigo())) {
            validateCodigoUnico(request.getCodigo());
        }
        UsuarioProyeccion usuario = usuarioProyeccionRepository
                .findByEmail(request.getUsuarioEmail())
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado")
                );
        notificacionMapper.updateEntity(request, notificacion);
        notificacion.setUsuario(usuario);
        Notificacion updated = notificacionRepository.save(notificacion);

        return notificacionMapper.toResponse(updated);

    }

    @Transactional

    public void deleteByCodigo(String codigo) {

        Notificacion notificacion = getNotificacionByCodigo(codigo);

        if (!notificacion.getLogs().isEmpty()) {

            throw new RuntimeException(

                    "No se puede eliminar la notificación porque tiene logs asociados"

            );

        }

        notificacionRepository.delete(notificacion);

    }

    @Transactional

    public void marcarComoEnviada(String codigo) {

        Notificacion notificacion = getNotificacionByCodigo(codigo);

        notificacion.setEnviada(true);

        notificacionRepository.save(notificacion);

    }

    public List<LogNotificacionResponse> findLogsByNotificacion(String codigo) {

        getNotificacionByCodigo(codigo);

        return notificacionMapper.toLogResponseList(logNotificacionRepository.findByNotificacionCodigo(codigo));

    }

    private Notificacion getNotificacionByCodigo(String codigo) {

        return notificacionRepository.findByCodigo(codigo)

                .orElseThrow(() ->

                        new RuntimeException("Notificación no encontrada")

                );

    }

    private void validateCodigoUnico(String codigo) {

        notificacionRepository.findByCodigo(codigo)

                .ifPresent(notificacion -> {

                    throw new RuntimeException(

                            "Ya existe una notificación con código: " + codigo

                    );

                });

    }


}
