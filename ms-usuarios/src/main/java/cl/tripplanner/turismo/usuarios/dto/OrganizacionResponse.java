package cl.tripplanner.turismo.usuarios.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OrganizacionResponse extends RepresentationModel<OrganizacionResponse>{
    private Integer id;
    private String nombre;
    private String dominioEmail;
    private Boolean activo;
}
