package cl.tripplanner.turismo.notificaciones.dto;

import lombok.Data;

@Data
public class NotificacionRequest {
    private String codigo;
    private String usuarioEmail;
    private String mensaje;
    private String canal;
}
