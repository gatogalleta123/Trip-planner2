package cl.tripplanner.turismo.hoteles.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelEvent {

    private Integer id;
    private String nombre;
    private Integer estrellas;
    private String accion;
}