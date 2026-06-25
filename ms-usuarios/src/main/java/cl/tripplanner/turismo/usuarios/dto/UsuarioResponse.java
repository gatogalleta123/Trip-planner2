package cl.tripplanner.turismo.usuarios.dto;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false) // Evita conflictos con los métodos equals de HATEOAS
public class UsuarioResponse extends RepresentationModel<UsuarioResponse>{
    private String nombre;
    private String email;
    private String telefono;
    private boolean activo;
    private LocalDate fechaRegistro;
    private Integer rolId;
    private Integer organizacionId;
}
