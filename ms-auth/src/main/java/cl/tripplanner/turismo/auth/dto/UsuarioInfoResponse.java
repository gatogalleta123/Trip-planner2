package cl.tripplanner.turismo.auth.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UsuarioInfoResponse {

    private String email;
    private String nombre;
    private String telefono;
    private boolean activo;
    private Integer organizacionId;
    private Integer rolId;
    private LocalDate fecha_registro;

}
