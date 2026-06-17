package cl.tripplanner.turismo.destinos.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaisEvent {

    private Integer id;
    private String nombre;
    private String codigoIso;
    private Boolean activo;
    private String accion; // CREADO / ELIMINADO
}