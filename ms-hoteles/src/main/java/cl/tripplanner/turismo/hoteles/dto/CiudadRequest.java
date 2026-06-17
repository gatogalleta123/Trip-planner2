package cl.tripplanner.turismo.hoteles.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CiudadRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El país es obligatorio")
    private String pais;

    @NotNull(message = "El estado es obligatorio")
    private Boolean activo;
}