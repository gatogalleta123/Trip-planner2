package cl.tripplanner.turismo.hoteles.service.impl;

import cl.tripplanner.turismo.hoteles.dto.HabitacionRequest;
import cl.tripplanner.turismo.hoteles.dto.HabitacionResponse;
import cl.tripplanner.turismo.hoteles.exception.ResourceNotFoundException;
import cl.tripplanner.turismo.hoteles.kafka.HabitacionEvent;
import cl.tripplanner.turismo.hoteles.kafka.HabitacionEventProducer;
import cl.tripplanner.turismo.hoteles.mapper.HabitacionMapper;
import cl.tripplanner.turismo.hoteles.modelo.Habitacion;
import cl.tripplanner.turismo.hoteles.modelo.Hotel;
import cl.tripplanner.turismo.hoteles.repository.HabitacionRepository;
import cl.tripplanner.turismo.hoteles.repository.HotelRepository;
import cl.tripplanner.turismo.hoteles.service.HabitacionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitacionServiceImpl implements HabitacionService {

    private final HabitacionRepository repository;
    private final HotelRepository hotelRepository;
    private final HabitacionEventProducer producer;

    @Override
    public List<HabitacionResponse> listar() {
        return repository.findAll()
                .stream()
                .map(HabitacionMapper::toDTO)
                .toList();
    }

    @Override
    public HabitacionResponse obtener(@NonNull Integer id) {

        Habitacion habitacion = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Habitación no encontrada"));

        return HabitacionMapper.toDTO(habitacion);
    }
@Override
public HabitacionResponse crear(HabitacionRequest dto) {

    Integer hotelId = Objects.requireNonNull(
            dto.getHotelId(),
            "El id del hotel no puede ser nulo"
    );

    Hotel hotel = hotelRepository.findById(hotelId)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Hotel no encontrado"));

    Habitacion habitacion = HabitacionMapper.toEntity(dto);
    habitacion.setHotel(hotel);

    Habitacion guardada = repository.save(habitacion);

    producer.enviarHabitacionCreada(
            HabitacionEvent.builder()
                    .id(guardada.getId())
                    .tipo(guardada.getTipo())
                    .precio(guardada.getPrecio())
                    .disponible(guardada.getDisponible())
                    .hotelNombre(hotel.getNombre())
                    .accion("CREADO")
                    .build()
    );

    return HabitacionMapper.toDTO(guardada);
}

@Override
public void eliminar(Integer id) {

    Integer habitacionId = Objects.requireNonNull(
            id,
            "El id no puede ser nulo"
    );

    Habitacion habitacion = repository.findById(habitacionId)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Habitación no encontrada"));

    producer.enviarHabitacionEliminada(
            HabitacionEvent.builder()
                    .id(habitacion.getId())
                    .tipo(habitacion.getTipo())
                    .precio(habitacion.getPrecio())
                    .disponible(habitacion.getDisponible())
                    .hotelNombre(
                            habitacion.getHotel() != null
                                    ? habitacion.getHotel().getNombre()
                                    : null
                    )
                    .accion("ELIMINADO")
                    .build()
    );

    repository.delete(habitacion);
}
}