package cl.tripplanner.turismo.vuelos.dto;

import lombok.Data;

@Data
public class RutaRequest {
    private String origen;
    private String destino;
    private Integer distanciaKm;
    private Boolean activo;
}