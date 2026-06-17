package cl.tripplanner.turismo.destinos.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaisResponse {
    private Integer id;
    private String nombre;
    private String codigoIso;
    private Boolean activo;
}