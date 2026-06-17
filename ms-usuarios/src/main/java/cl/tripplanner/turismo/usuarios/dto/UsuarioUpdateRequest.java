package cl.tripplanner.turismo.usuarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioUpdateRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;


     @NotBlank(message = "El número de teléfono es obligatorio")
    private String telefono;

    private boolean activo;

    @NotNull(message = "El rol es obligatorio")
    private Integer rolId;

    private Integer organizacionId;
}
