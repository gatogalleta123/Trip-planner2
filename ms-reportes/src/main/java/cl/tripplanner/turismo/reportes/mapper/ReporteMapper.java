package cl.tripplanner.turismo.reportes.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.tripplanner.turismo.reportes.dto.ReporteRequest;
import cl.tripplanner.turismo.reportes.dto.ReporteResponse;
import cl.tripplanner.turismo.reportes.model.Reporte;

@Mapper(componentModel = "spring")
public interface ReporteMapper {

    // Convierte Request a Entidad.
    // Ignoramos ID, fuente y ejecuciones
    // porque la fuente se resuelve en el service.
    @Mapping(target = "id",            ignore = true)
    @Mapping(target = "fuente",        ignore = true)
    @Mapping(target = "ejecuciones",   ignore = true)
    Reporte toEntity(ReporteRequest request);

    // Convierte Entidad a Response
    // aplanando datos de Fuente.
    @Mapping(target = "fuenteId",      source = "fuente.id")
    @Mapping(target = "fuenteNombre",  source = "fuente.nombre")
    ReporteResponse toResponse(
            Reporte reporte
    );

    List<ReporteResponse> toResponseList(
            List<Reporte> reportes
    );

    // Actualiza entidad persistida
    // sin alterar ID ni relaciones.
    @Mapping(target = "id",            ignore = true)
    @Mapping(target = "fuente",        ignore = true)
    @Mapping(target = "ejecuciones",   ignore = true)
    void updateEntity(
            ReporteRequest request,
            @MappingTarget Reporte reporte
    );
}
