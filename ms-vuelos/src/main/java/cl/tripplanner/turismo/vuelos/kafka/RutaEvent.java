package cl.tripplanner.turismo.vuelos.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RutaEvent {

    private Integer id;
    private String origen;
    private String destino;
    private Integer distanciaKm;
    private Boolean activo;
    private String accion;
}