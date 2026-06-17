package cl.tripplanner.turismo.usuarios.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.tripplanner.turismo.usuarios.dto.OrganizacionRequest;
import cl.tripplanner.turismo.usuarios.dto.OrganizacionResponse;
import cl.tripplanner.turismo.usuarios.model.Organizacion;

@Mapper(componentModel = "spring")
public interface OrganizacionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuarios", ignore = true)
    Organizacion toEntity(OrganizacionRequest request);

    OrganizacionResponse toResponse(Organizacion organizacion);

    List<OrganizacionResponse> toOrganizacionResponseList(List<Organizacion> organizaciones);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuarios", ignore = true)
    void updateEntity(OrganizacionRequest request, @MappingTarget Organizacion organizacion);
}
