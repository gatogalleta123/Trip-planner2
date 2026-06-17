package cl.tripplanner.turismo.vuelos.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RutaEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "rutas-topic";

    public void enviarRutaCreada(RutaEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }

    public void enviarRutaEliminada(RutaEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}