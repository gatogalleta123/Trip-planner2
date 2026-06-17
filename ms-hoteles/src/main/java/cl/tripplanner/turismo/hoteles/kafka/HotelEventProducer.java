package cl.tripplanner.turismo.hoteles.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void enviarHotelCreado(HotelEvent event) {
        kafkaTemplate.send("hotel-creado", event);
    }

    public void enviarHotelEliminado(HotelEvent event) {
        kafkaTemplate.send("hotel-eliminado", event);
    }
}