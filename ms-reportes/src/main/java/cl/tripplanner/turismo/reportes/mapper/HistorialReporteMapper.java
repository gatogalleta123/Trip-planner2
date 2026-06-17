package cl.tripplanner.turismo.reportes.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.tripplanner.turismo.reportes.dto.HistorialReporteRequest;
import cl.tripplanner.turismo.reportes.dto.HistorialReporteResponse;
import cl.tripplanner.turismo.reportes.model.HistorialReporte;

@Mapper(componentModel = "spring")
public interface HistorialReporteMapper {

    // Convierte Request a Entidad.
    // Ignoramos ID y relación reporte
    // porque se resuelve en el service.
    @Mapping(target = "id",         ignore = true)
    @Mapping(target = "reporte",    ignore = true)
    @Mapping(target = "fechaEjecucion", ignore = true)
    HistorialReporte toEntity(
            HistorialReporteRequest request
    );

    // Convierte Entidad a Response
    // aplanando datos del reporte.
    @Mapping(
            target = "reporteId",
            source = "reporte.id"
    )
    @Mapping(
            target = "reporteNombre",
            source = "reporte.nombre"
    )
    HistorialReporteResponse toResponse(
            HistorialReporte historial
    );

    List<HistorialReporteResponse>
    toResponseList(
            List<HistorialReporte> historial
    );

    // Actualiza entidad persistida
    // sin alterar ID ni relaciones.
    @Mapping(target = "id",         ignore = true)
    @Mapping(target = "reporte",    ignore = true)
    @Mapping(target = "fechaEjecucion", ignore = true)
    void updateEntity(
            HistorialReporteRequest request,
            @MappingTarget HistorialReporte historial
    );
}