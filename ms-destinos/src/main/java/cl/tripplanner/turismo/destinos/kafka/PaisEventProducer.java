package cl.tripplanner.turismo.destinos.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaisEventProducer {

    private final KafkaTemplate<String, PaisEvent> kafkaTemplate;

    private static final String TOPIC = "paises-topic";

    public void enviarPaisCreado(PaisEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }

    public void enviarPaisEliminado(PaisEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}