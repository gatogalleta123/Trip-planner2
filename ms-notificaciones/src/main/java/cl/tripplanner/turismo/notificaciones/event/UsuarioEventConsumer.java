package cl.tripplanner.turismo.notificaciones.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import cl.tripplanner.common.event.UsuarioCreatedEvent;
import cl.tripplanner.common.event.UsuarioDeletedEvent;
import cl.tripplanner.common.event.UsuarioUpdatedEvent;
import cl.tripplanner.turismo.notificaciones.service.NotificacionService;
import cl.tripplanner.turismo.notificaciones.service.UsuarioProyeccionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsuarioEventConsumer {
    private final UsuarioProyeccionService usuarioProyeccionService;
    @KafkaListener(
        topics =  "usuarios.usuario.created",
        groupId = "ms-notificaciones",
        properties = {"spring.json.value.default.type=cl.tripplanner.common.event.UsuarioCreatedEvent"}
    )
    @Transactional
    public void onUsuarioCreated(UsuarioCreatedEvent event) {
        log.info("Evento recibido: created, Email usuario:  {}", event.getEmail());
        usuarioProyeccionService.save(event.getEmail(), event.getNombre(), event.getActivo());
    }

    @KafkaListener(
    topics = "usuarios.usuario.updated",
    groupId = "ms-notificaciones",
    properties = {
        "spring.json.value.default.type=cl.tripplanner.common.event.UsuarioUpdatedEvent"
    }
    )
    @Transactional
    public void onUsuarioUpdated(UsuarioUpdatedEvent event) {
        log.info("Evento recibido: updated, Email usuario: {}",event.getEmail());
        usuarioProyeccionService.update(event.getEmail(), event.getNombre(), event.getActivo());
    }

    @KafkaListener(
    topics = "usuarios.usuario.deleted",
    groupId = "ms-notificaciones",
    properties = {"spring.json.value.default.type=cl.tripplanner.common.event.UsuarioDeletedEvent"}
    )
    @Transactional
    public void onUsuarioDeleted(UsuarioDeletedEvent event) {
        log.info("Evento recibido: deleted, Email usuario: {}",event.getEmail());
        usuarioProyeccionService.deleteByEmail(event.getEmail());
    }
}
