package cl.tripplanner.turismo.destinos.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DestinoEventProducer {

    private final KafkaTemplate<String, DestinoEvent> kafkaTemplate;

    private static final String TOPIC = "destinos-topic";

    public void enviarDestinoCreado(DestinoEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }

    public void enviarDestinoEliminado(DestinoEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}