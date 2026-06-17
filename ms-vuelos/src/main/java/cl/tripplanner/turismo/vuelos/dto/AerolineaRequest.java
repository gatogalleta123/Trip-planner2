package cl.tripplanner.turismo.vuelos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AerolineaRequest {

    private String nombre;
    private String codigoIata;
    private Boolean activo;
}