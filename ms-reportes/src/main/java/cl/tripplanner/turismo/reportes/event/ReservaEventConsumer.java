package cl.tripplanner.turismo.reportes.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import cl.tripplanner.common.event.ReservaCreatedEvent;
import cl.tripplanner.common.event.ReservaDeletedEvent;
import cl.tripplanner.common.event.ReservaUpdatedEvent;
import cl.tripplanner.common.event.UsuarioCreatedEvent;
import cl.tripplanner.common.event.UsuarioDeletedEvent;
import cl.tripplanner.common.event.UsuarioUpdatedEvent;
import cl.tripplanner.turismo.reportes.service.ReservaProyeccionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservaEventConsumer {
    private final ReservaProyeccionService reservaProyeccionService;
    @KafkaListener(
        topics =  "reservas.reserva.created",
        groupId = "ms-reservas",
        properties = {"spring.json.value.default.type=cl.tripplanner.common.event.ReservaCreatedEvent"}
    )
    @Transactional
    public void onReservaCreated(ReservaCreatedEvent event) {
        log.info("Evento recibido: created, Reserva Id:  {}", event.getId());
        reservaProyeccionService.save(event.getId(), event.getClienteId(), event.getEstado());
    }

    @KafkaListener(
    topics = "reservas.reserva.updated",
    groupId = "ms-reservas",
    properties = {
        "spring.json.value.default.type=cl.tripplanner.common.event.ReservaUpdatedEvent"
    }
    )
    @Transactional
    public void onReservaUpdated(ReservaUpdatedEvent event) {
        log.info("Evento recibido: updated, Reserva Id: {}",event.getId());
        reservaProyeccionService.update(event.getId(), event.getClienteId(), event.getEstado());
    }

    @KafkaListener(
    topics = "reservas.reserva.deleted",
    groupId = "ms-reservas",
    properties = {"spring.json.value.default.type=cl.tripplanner.common.event.ReservaDeletedEvent"}
    )
    @Transactional
    public void onReservaDeleted(ReservaDeletedEvent event) {
        log.info("Evento recibido: deleted, Reserva Id: {}",event.getId());
        reservaProyeccionService.deleteById(event.getId());
    }
}
