package cl.tripplanner.turismo.notificaciones.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificacionResponse {
    @NotNull
    private String codigo;

    @NotNull
    private String usuarioEmail;

    @NotNull
    private String mensaje;

    @NotNull
    private String canal;

    @NotNull
    private String nombreUsuario;

    @NotNull
    private String enviada;
}
