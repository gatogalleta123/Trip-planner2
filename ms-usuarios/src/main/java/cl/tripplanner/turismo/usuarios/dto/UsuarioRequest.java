package cl.tripplanner.turismo.usuarios.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    private String email;

     @NotBlank(message = "El número de teléfono es obligatorio")
    private String telefono;

    @NotNull(message = "El estado del usuario es obligatorio")
    private boolean activo;

    @NotNull(message = "El rol es obligatorio")
    private Integer rolId;

    private Integer organizacionId;
}
