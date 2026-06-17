package cl.tripplanner.turismo.hoteles.mapper;

import cl.tripplanner.turismo.hoteles.dto.*;
import cl.tripplanner.turismo.hoteles.modelo.Ciudad;

public class CiudadMapper {

    public static CiudadResponse toDTO(Ciudad ciudad) {

        return CiudadResponse.builder()
                .id(ciudad.getId())
                .nombre(ciudad.getNombre())
                .pais(ciudad.getPais())
                .activo(ciudad.getActivo())
                .build();
    }

public static Ciudad toEntity(CiudadRequest dto) {

    return Ciudad.builder()
            .nombre(dto.getNombre())
            .pais(dto.getPais())
            .activo(dto.getActivo())
            .build();
}
}