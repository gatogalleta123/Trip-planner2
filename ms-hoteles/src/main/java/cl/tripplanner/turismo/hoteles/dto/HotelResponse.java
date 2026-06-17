package cl.tripplanner.turismo.hoteles.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelResponse {

    private Integer id;
    private String nombre;
    private Integer estrellas;
    private Boolean activo;
}