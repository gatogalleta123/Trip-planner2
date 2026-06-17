package cl.tripplanner.turismo.hoteles.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "La ciudad es obligatoria")
    private Integer ciudadId;

    @NotNull(message = "Las estrellas son obligatorias")
    private Integer estrellas;

    @NotNull(message = "El estado es obligatorio")
    private Boolean activo;
}