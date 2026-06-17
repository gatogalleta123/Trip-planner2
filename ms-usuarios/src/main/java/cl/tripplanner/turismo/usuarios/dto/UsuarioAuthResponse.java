package cl.tripplanner.turismo.usuarios.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UsuarioAuthResponse {
    private String email;
    private String metodoAutenticacion;
    private boolean activo;
    private LocalDateTime ultimoLogin;
}
