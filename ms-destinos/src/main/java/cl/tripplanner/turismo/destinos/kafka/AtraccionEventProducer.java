package cl.tripplanner.turismo.destinos.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AtraccionEventProducer {

    private final KafkaTemplate<String, AtraccionEvent> kafkaTemplate;

    private static final String TOPIC = "atracciones-topic";

    public void enviarAtraccionCreada(AtraccionEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }

    public void enviarAtraccionEliminada(AtraccionEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}