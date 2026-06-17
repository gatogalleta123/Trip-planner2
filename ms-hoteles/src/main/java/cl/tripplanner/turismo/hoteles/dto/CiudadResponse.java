package cl.tripplanner.turismo.hoteles.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CiudadResponse {

    private Integer id;
    private String nombre;
    private String pais;
    private Boolean activo;
}