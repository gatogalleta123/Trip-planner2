package cl.tripplanner.turismo.vuelos.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AerolineaEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "aerolineas-topic";

    public void enviarAerolineaCreada(AerolineaEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }

    public void enviarAerolineaEliminada(AerolineaEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}