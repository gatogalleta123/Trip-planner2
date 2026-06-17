package cl.tripplanner.turismo.destinos.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DestinoResponse {
    private Integer id;
    private String nombre;
    private String tipo;
    private Boolean activo;
    private String pais;
}