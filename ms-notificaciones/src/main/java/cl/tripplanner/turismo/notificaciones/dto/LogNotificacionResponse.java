package cl.tripplanner.turismo.notificaciones.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LogNotificacionResponse {
    @NotBlank
    private Long id;

    @NotBlank
    private LocalDateTime fecha;

    @NotBlank
    private String estado;
    
    @NotBlank
    private String detalle;
}
