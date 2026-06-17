package cl.tripplanner.turismo.hoteles.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HabitacionEvent {

    private Integer id;
    private String tipo;
    private BigDecimal precio;
    private Boolean disponible;
    private String hotelNombre;
    private String accion;
}