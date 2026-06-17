package cl.tripplanner.turismo.usuarios.event;


import cl.tripplanner.common.event.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsuarioEventProducer {

    private static final String TOPIC_BASE = "usuarios.usuario";
    private static final String EMAIL_NOT_NULL = "El email no puede ser null";
    private static final String TOPIC_NOT_NULL = "El topic no puede ser null";

    private final KafkaTemplate<String, UsuarioEvent> kafkaTemplate;

    private void send(
            UsuarioEvent event,
            String eventType
    ) {

        String topic = Objects.requireNonNull(
                String.format("%s.%s", TOPIC_BASE, eventType),
                TOPIC_NOT_NULL
        );

        String email = Objects.requireNonNull(
                event.getEmail(),
                EMAIL_NOT_NULL
        );

        log.debug("********************");
        log.debug("********************");
        log.debug("********************");
        log.debug("");
        log.debug(
                "Enviando evento Kafka → topic: {}, key: {}",
                topic,
                email
        );
        log.debug("");
        log.debug("********************");
        log.debug("********************");
        log.debug("********************");

        kafkaTemplate.send(
                topic,
                email,
                event
        );
    }

    public void sendCreated(
            UsuarioCreatedEvent event
    ) {
        send(event, "created");
    }

    public void sendUpdated(
            UsuarioUpdatedEvent event
    ) {
        send(event, "updated");
    }

    public void sendDeleted(
            UsuarioDeletedEvent event
    ) {
        send(event, "deleted");
    }
}