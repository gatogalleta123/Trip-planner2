package cl.tripplanner.turismo.vuelos.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AerolineaEvent {

    private Integer id;
    private String nombre;
    private String codigoIata;
    private Boolean activo;
    private String accion;
}