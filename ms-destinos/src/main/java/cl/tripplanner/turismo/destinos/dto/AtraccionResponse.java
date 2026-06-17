package cl.tripplanner.turismo.destinos.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AtraccionResponse {
    private Integer id;
    private String nombre;
    private BigDecimal precio;
    private Boolean disponible;
    private String destino;
}