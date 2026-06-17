package cl.tripplanner.turismo.destinos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaisRequest {

    @NotBlank
    private String nombre;

    @NotBlank
    private String codigoIso;

    @NotNull
    private Boolean activo;
}