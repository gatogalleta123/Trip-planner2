package cl.tripplanner.turismo.notificaciones.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioProyeccionResponse {

    private String email;
    private String nombre;
    private Boolean activo;
}