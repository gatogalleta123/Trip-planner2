package cl.tripplanner.turismo.vuelos.mapper;

import cl.tripplanner.turismo.vuelos.dto.RutaRequest;
import cl.tripplanner.turismo.vuelos.dto.RutaResponse;
import cl.tripplanner.turismo.vuelos.modelo.Ruta;

public class RutaMapper {

    public static Ruta toEntity(RutaRequest req) {
        return Ruta.builder()
                .origen(req.getOrigen())
                .destino(req.getDestino())
                .distanciaKm(req.getDistanciaKm())
                .activo(req.getActivo())
                .build();
    }

    public static RutaResponse toResponse(Ruta entity) {
        return RutaResponse.builder()
                .id(entity.getId())
                .origen(entity.getOrigen())
                .destino(entity.getDestino())
                .distanciaKm(entity.getDistanciaKm())
                .activo(entity.getActivo())
                .build();
    }
}