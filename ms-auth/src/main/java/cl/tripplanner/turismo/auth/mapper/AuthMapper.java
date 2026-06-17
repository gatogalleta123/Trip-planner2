package cl.tripplanner.turismo.auth.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.tripplanner.turismo.auth.dto.RegisterRequest;
import cl.tripplanner.turismo.auth.dto.UpdateRequest;
import cl.tripplanner.turismo.auth.dto.UsuarioAuthResponse;
import cl.tripplanner.turismo.auth.model.UsuarioAuth;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(target = "metodoAutenticacion", ignore = true)
    @Mapping(target = "auditorias", ignore = true)
    @Mapping(target = "ultimoLogin", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "passwordHash", source = "password")
    UsuarioAuth toEntity(RegisterRequest request);

    @Mapping(target = "metodoAutenticacion", source = "metodoAutenticacion.descripcion")
    UsuarioAuthResponse toResponse(UsuarioAuth usuario);

    List<UsuarioAuthResponse> toResponseList(List<UsuarioAuth> usuarios);

    @Mapping(target = "metodoAutenticacion", ignore = true)
    @Mapping(target = "auditorias", ignore = true)
    @Mapping(target = "ultimoLogin", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "passwordHash", source = "password")
    void updateEntity(RegisterRequest request, @MappingTarget UsuarioAuth usuario);

    @Mapping(target = "metodoAutenticacion", ignore = true)
    @Mapping(target = "auditorias", ignore = true)
    @Mapping(target = "ultimoLogin", ignore = true)
    void updateEntity(UpdateRequest request, @MappingTarget UsuarioAuth usuario);
}
