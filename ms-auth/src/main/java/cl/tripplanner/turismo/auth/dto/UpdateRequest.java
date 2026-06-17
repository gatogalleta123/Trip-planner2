package cl.tripplanner.turismo.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateRequest {
    @NotBlank(message = "El método de autenticación es obligatorio")
    private String metodoAutenticacion;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    @NotNull(message = "El estado del usuario es obligatorio")
    private Boolean activo;
}
