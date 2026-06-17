package cl.tripplanner.turismo.vuelos.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VueloResponse {
    private Integer id;
    private Integer aerolineaId;
    private Integer rutaId;
    private String fechaSalida;
    private String precio;
}