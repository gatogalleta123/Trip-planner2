package cl.tripplanner.turismo.vuelos.mapper;

import cl.tripplanner.turismo.vuelos.dto.VueloResponse;
import cl.tripplanner.turismo.vuelos.modelo.Vuelo;

public class VueloMapper {

    public static VueloResponse toResponse(Vuelo entity) {
        return VueloResponse.builder()
                .id(entity.getId())
                .aerolineaId(entity.getAerolinea().getId())
                .rutaId(entity.getRuta().getId())
                .fechaSalida(entity.getFechaSalida().toString())
                .precio(entity.getPrecio().toString())
                .build();
    }
}