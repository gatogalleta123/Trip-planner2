package cl.tripplanner.turismo.vuelos.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AerolineaResponse {

    private Integer id;
    private String nombre;
    private String codigoIata;
    private Boolean activo;
}