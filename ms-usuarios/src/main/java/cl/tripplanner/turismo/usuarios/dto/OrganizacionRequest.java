package cl.tripplanner.turismo.usuarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrganizacionRequest {
    @NotBlank(message = "El nombre de la organización es obligatorio")
    private String nombre;
    @NotNull(message = "El dominio de email es obligatorio")
    private String dominioEmail;
    @NotNull(message = "El estado de la organización es obligatorio")
    private Boolean activo;
}
