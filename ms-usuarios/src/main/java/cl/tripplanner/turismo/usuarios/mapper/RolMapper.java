package cl.tripplanner.turismo.usuarios.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.tripplanner.turismo.usuarios.dto.RolRequest;
import cl.tripplanner.turismo.usuarios.dto.RolResponse;
import cl.tripplanner.turismo.usuarios.model.Rol;

@Mapper(componentModel = "spring")
public interface RolMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuarios", ignore = true)
    Rol toEntity(RolRequest request);

    RolResponse toResponse(Rol rol);

    List<RolResponse> toRolResponseList(List<Rol> roles);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuarios", ignore = true)
    void updateEntity(RolRequest request,@MappingTarget Rol rol);
}
