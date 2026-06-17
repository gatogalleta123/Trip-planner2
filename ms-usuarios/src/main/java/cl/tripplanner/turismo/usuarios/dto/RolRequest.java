package cl.tripplanner.turismo.usuarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RolRequest {
    @NotBlank(message = "El nombre del rol es obligatorio")
    private String nombre;
    @NotBlank(message = "La descripción del rol es obligatoria")
    private String descripcion;
    @NotNull(message = "El estado del rol es obligatorio")
    private Boolean activo;
}