package cl.tripplanner.turismo.destinos.mapper;

import cl.tripplanner.turismo.destinos.dto.*;
import cl.tripplanner.turismo.destinos.modelo.Pais;

public class PaisMapper {

    public static Pais toEntity(PaisRequest dto) {
        return Pais.builder()
                .nombre(dto.getNombre())
                .codigoIso(dto.getCodigoIso())
                .activo(dto.getActivo())
                .build();
    }

    public static PaisResponse toDTO(Pais pais) {
        return PaisResponse.builder()
                .id(pais.getId())
                .nombre(pais.getNombre())
                .codigoIso(pais.getCodigoIso())
                .activo(pais.getActivo())
                .build();
    }
}