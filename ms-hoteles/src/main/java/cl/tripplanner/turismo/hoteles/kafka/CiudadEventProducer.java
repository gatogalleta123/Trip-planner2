package cl.tripplanner.turismo.hoteles.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CiudadEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "ciudades-topic";

    public void enviarCiudadCreada(CiudadEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }

    public void enviarCiudadEliminada(CiudadEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}