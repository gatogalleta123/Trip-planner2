package cl.tripplanner.turismo.destinos.mapper;

import cl.tripplanner.turismo.destinos.dto.AtraccionRequest;
import cl.tripplanner.turismo.destinos.dto.AtraccionResponse;
import cl.tripplanner.turismo.destinos.modelo.Atraccion;

public class AtraccionMapper {

    public static AtraccionResponse toDTO(Atraccion atraccion) {

        return AtraccionResponse.builder()
                .id(atraccion.getId())
                .nombre(atraccion.getNombre())
                .precio(atraccion.getPrecio())
                .disponible(atraccion.getDisponible())
                .build();
    }

    public static Atraccion toEntity(AtraccionRequest dto) {

        return Atraccion.builder()
                .nombre(dto.getNombre())
                .precio(dto.getPrecio())
                .disponible(dto.getDisponible())
                .build();
    }
}