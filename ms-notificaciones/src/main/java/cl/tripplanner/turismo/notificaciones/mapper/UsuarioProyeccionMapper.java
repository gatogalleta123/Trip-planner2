package cl.tripplanner.turismo.notificaciones.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import cl.tripplanner.turismo.notificaciones.dto.UsuarioProyeccionResponse;
import cl.tripplanner.turismo.notificaciones.model.UsuarioProyeccion;

@Mapper(componentModel = "spring")
public interface UsuarioProyeccionMapper {

    @Mapping(target = "email", source = "email")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "activo", source = "activo")
    UsuarioProyeccionResponse toResponse(UsuarioProyeccion usuarioProyeccion);

    List<UsuarioProyeccionResponse> toResponseList(List<UsuarioProyeccion> usuariosProyeccion);
}
