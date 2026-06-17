package cl.tripplanner.turismo.usuarios.dto;

import lombok.Data;

@Data
public class RolResponse {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Boolean activo;
}
