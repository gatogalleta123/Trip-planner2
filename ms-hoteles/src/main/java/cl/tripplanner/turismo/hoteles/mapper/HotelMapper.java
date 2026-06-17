package cl.tripplanner.turismo.hoteles.mapper;

import cl.tripplanner.turismo.hoteles.dto.*;
import cl.tripplanner.turismo.hoteles.modelo.Hotel;

public class HotelMapper {

    public static HotelResponse toDTO(Hotel hotel) {

        return HotelResponse.builder()
                .id(hotel.getId())
                .nombre(hotel.getNombre())
                .estrellas(hotel.getEstrellas())
                .activo(hotel.getActivo())
                .build();
    }

    public static Hotel toEntity(HotelRequest dto) {

        return Hotel.builder()
                .nombre(dto.getNombre())
                .estrellas(dto.getEstrellas())
                .activo(dto.getActivo())
                .build();
    }
}