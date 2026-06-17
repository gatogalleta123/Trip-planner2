package cl.tripplanner.turismo.usuarios.dto;

import lombok.Data;

@Data
public class OrganizacionResponse {
    private Integer id;
    private String nombre;
    private String dominioEmail;
    private Boolean activo;
}
