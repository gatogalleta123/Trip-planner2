package cl.tripplanner.turismo.hoteles.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CiudadEvent {

    private Integer id;
    private String nombre;
    private String pais;
    private Boolean activo;
    private String accion;
}