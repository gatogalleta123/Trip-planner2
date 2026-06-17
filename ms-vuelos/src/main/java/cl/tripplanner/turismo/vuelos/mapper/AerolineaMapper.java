package cl.tripplanner.turismo.vuelos.mapper;

import cl.tripplanner.turismo.vuelos.dto.AerolineaRequest;
import cl.tripplanner.turismo.vuelos.dto.AerolineaResponse;
import cl.tripplanner.turismo.vuelos.modelo.Aerolinea;

public class AerolineaMapper {

    public static Aerolinea toEntity(AerolineaRequest req) {
        if (req == null) return null;

        return Aerolinea.builder()
                .nombre(req.getNombre())
                .codigoIata(req.getCodigoIata())
                .activo(req.getActivo())
                .build();
    }

    public static AerolineaResponse toResponse(Aerolinea entity) {
        if (entity == null) return null;

        return AerolineaResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .codigoIata(entity.getCodigoIata())
                .activo(entity.getActivo())
                .build();
    }
}