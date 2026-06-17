package cl.tripplanner.turismo.vuelos.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RutaResponse {
    private Integer id;
    private String origen;
    private String destino;
    private Integer distanciaKm;
    private Boolean activo;
}