package cl.tripplanner.turismo.reportes.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
@Data
public class ReporteRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 150, message = "El nombre no puede superar 150 caracteres")
    private String nombre;

    @NotNull(message = "La fuente es obligatoria")
    @Positive(message = "El ID de fuente debe ser positivo")
    private Long fuenteId;

    @NotBlank(message = "El formato es obligatorio")
    @Pattern(
            regexp = "pdf|csv|json",
            message = "El formato debe ser pdf, csv o json"
    )
    private String formato;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;
}
