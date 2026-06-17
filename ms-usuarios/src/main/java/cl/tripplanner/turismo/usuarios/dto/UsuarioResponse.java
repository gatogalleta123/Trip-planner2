package cl.tripplanner.turismo.usuarios.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UsuarioResponse {
    private String nombre;
    private String email;
    private String telefono;
    private boolean activo;
    private LocalDate fechaRegistro;
    private Integer rolId;
    private Integer organizacionId;
}
