package cl.tripplanner.turismo.reportes.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class HistorialReporteRequest {

    @NotNull(message = "El reporte es obligatorio")
    @Positive(message = "El ID del reporte debe ser positivo")
    private Long reporteId;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(
            regexp = "pendiente|generado|fallido",
            message = "El estado debe ser pendiente, generado o fallido"
    )
    private String estado;

    @Size(
            max = 255,
            message = "La URL no puede superar 255 caracteres"
    )
    private String urlResultado;
}