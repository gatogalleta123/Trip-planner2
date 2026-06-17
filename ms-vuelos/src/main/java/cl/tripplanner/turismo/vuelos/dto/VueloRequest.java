package cl.tripplanner.turismo.vuelos.dto;

import lombok.Data;

@Data
public class VueloRequest {
    private Integer aerolineaId;
    private Integer rutaId;
    private String fechaSalida;  // iso string
    private String precio;       // BigDecimal como String
}