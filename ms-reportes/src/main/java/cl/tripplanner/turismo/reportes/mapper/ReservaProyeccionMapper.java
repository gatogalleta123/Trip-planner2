package cl.tripplanner.turismo.reportes.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import cl.tripplanner.turismo.reportes.dto.ReservaProyeccionResponse;
import cl.tripplanner.turismo.reportes.model.ReservaProyeccion;

@Mapper(componentModel = "spring")
public interface ReservaProyeccionMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "clienteId", source = "clienteId")
    @Mapping(target = "estado", source = "estado")
    ReservaProyeccionResponse toResponse(ReservaProyeccion reservaProyeccion);

    List<ReservaProyeccionResponse> toResponseList(List<ReservaProyeccion> reservaProyeccion);
}
