package cl.tripplanner.turismo.usuarios.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class RolResponse extends RepresentationModel<RolResponse>{
    private Integer id;
    private String nombre;
    private String descripcion;
    private Boolean activo;
}
