package cl.tripplanner.turismo.vuelos.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VueloEvent {

    private Integer id;
    private String aerolineaNombre;
    private String origen;
    private String destino;
    private String fechaSalida;
    private String precio;
    private String accion;
}