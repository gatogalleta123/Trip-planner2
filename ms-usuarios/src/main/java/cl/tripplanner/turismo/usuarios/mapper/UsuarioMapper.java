package cl.tripplanner.turismo.usuarios.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.tripplanner.turismo.usuarios.dto.UsuarioRequest;
import cl.tripplanner.turismo.usuarios.dto.UsuarioResponse;
import cl.tripplanner.turismo.usuarios.dto.UsuarioUpdateRequest;
import cl.tripplanner.turismo.usuarios.model.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "organizacion", ignore = true)
    Usuario toEntity(UsuarioRequest request);

    @Mapping(target = "rolId", source = "rol.id")
    @Mapping(target = "organizacionId", source = "organizacion.id")
    UsuarioResponse toResponse(Usuario usuario);

    @Mapping(target = "rolId", source = "rol.id")
    @Mapping(target = "organizacionId", source = "organizacion.id")
    List<UsuarioResponse> toResponseList(List<Usuario> usuarios);



    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "organizacion", ignore = true)
    void updateEntity(UsuarioRequest request, @MappingTarget Usuario usuario);

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "organizacion", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    void updateEntity(UsuarioUpdateRequest request, @MappingTarget Usuario usuario);

    
}
