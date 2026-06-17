package cl.tripplanner.turismo.destinos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DestinoRequest {

    @NotBlank
    private String nombre;

    @NotNull
    private Integer paisId;

    @NotBlank
    private String tipo;

    @NotNull
    private Boolean activo;
}