package cl.tripplanner.turismo.hoteles.mapper;

import cl.tripplanner.turismo.hoteles.dto.*;
import cl.tripplanner.turismo.hoteles.modelo.Habitacion;

public class HabitacionMapper {

    public static HabitacionResponse toDTO(Habitacion habitacion) {

        return HabitacionResponse.builder()
                .id(habitacion.getId())
                .tipo(habitacion.getTipo())
                .precio(habitacion.getPrecio())
                .disponible(habitacion.getDisponible())
                .build();
    }

    public static Habitacion toEntity(HabitacionRequest dto) {

        return Habitacion.builder()
                .tipo(dto.getTipo())
                .precio(dto.getPrecio())
                .disponible(dto.getDisponible())
                .build();
    }
}