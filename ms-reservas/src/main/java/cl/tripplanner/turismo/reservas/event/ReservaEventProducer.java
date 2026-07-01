package cl.tripplanner.turismo.reservas.event;

import cl.tripplanner.common.event.ReservaCreatedEvent;
import cl.tripplanner.common.event.ReservaDeletedEvent;
import cl.tripplanner.common.event.ReservaUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservaEventProducer {

    private static final String TOPIC_BASE = "reservas.reserva";
    private static final String TOPIC_NOT_NULL = "El topic no puede ser null";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private void send(Object event, String eventType, String key) {
        String topic = Objects.requireNonNull(
                String.format("%s.%s", TOPIC_BASE, eventType),
                TOPIC_NOT_NULL
        );

        log.debug("Enviando evento Kafka → topic: {}, key: {}", topic, key);
        kafkaTemplate.send(topic, key, event);
    }

    public void sendCreated(ReservaCreatedEvent event) {
        send(event, "created", event.getId());
    }

    public void sendUpdated(ReservaUpdatedEvent event) {
        send(event, "updated", event.getId());
    }

    public void sendDeleted(ReservaDeletedEvent event) {
        send(event, "deleted", event.getId());
    }
}
