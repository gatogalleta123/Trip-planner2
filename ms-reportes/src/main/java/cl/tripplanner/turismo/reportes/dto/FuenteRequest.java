package cl.tripplanner.turismo.reportes.dto;


import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class FuenteRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 120, message = "El nombre no puede superar 120 caracteres")
    private String nombre;

    @NotBlank(message = "El tipo es obligatorio")
    @Size(max = 50, message = "El tipo no puede superar 50 caracteres")
    private String tipo;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;
}