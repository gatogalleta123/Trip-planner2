package cl.tripplanner.turismo.hoteles.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HabitacionEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "habitaciones-topic";

    public void enviarHabitacionCreada(HabitacionEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }

    public void enviarHabitacionEliminada(HabitacionEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}