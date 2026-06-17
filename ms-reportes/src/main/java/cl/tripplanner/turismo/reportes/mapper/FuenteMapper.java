package cl.tripplanner.turismo.reportes.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.tripplanner.turismo.reportes.dto.FuenteRequest;
import cl.tripplanner.turismo.reportes.dto.FuenteResponse;
import cl.tripplanner.turismo.reportes.model.Fuente;

@Mapper(componentModel = "spring")
public interface FuenteMapper {

    // Transforma el Request a Entidad.
    // Ignoramos el ID y la colección de reportes
    // porque se manejan por separado.
    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "reportes",  ignore = true)
    Fuente toEntity(FuenteRequest request);

    // Transforma Entidad a Response.
    FuenteResponse toResponse(Fuente fuente);

    List<FuenteResponse> toResponseList(
            List<Fuente> fuentes
    );

    // Actualiza la entidad persistida sin modificar ID
    // ni relaciones administradas por JPA.
    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "reportes",  ignore = true)
    void updateEntity(
            FuenteRequest request,
            @MappingTarget Fuente fuente
    );
}