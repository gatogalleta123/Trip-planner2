package cl.tripplanner.turismo.vuelos.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VueloEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "vuelos-topic";

    public void enviarVueloCreado(VueloEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }

    public void enviarVueloEliminado(VueloEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}