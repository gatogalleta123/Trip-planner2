package cl.tripplanner.turismo.destinos.mapper;

import cl.tripplanner.turismo.destinos.dto.DestinoRequest;
import cl.tripplanner.turismo.destinos.dto.DestinoResponse;
import cl.tripplanner.turismo.destinos.modelo.Destino;

public class DestinoMapper {

    public static DestinoResponse toDTO(Destino destino) {

        return DestinoResponse.builder()
                .id(destino.getId())
                .nombre(destino.getNombre())
                .tipo(destino.getTipo())
                .activo(destino.getActivo())
                .build();
    }

    public static Destino toEntity(DestinoRequest dto) {

        return Destino.builder()
                .nombre(dto.getNombre())
                .tipo(dto.getTipo())
                .activo(dto.getActivo())
                .build();
    }
}