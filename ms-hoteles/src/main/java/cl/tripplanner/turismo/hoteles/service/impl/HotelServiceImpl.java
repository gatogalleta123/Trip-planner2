package cl.tripplanner.turismo.hoteles.service.impl;

import cl.tripplanner.turismo.hoteles.dto.HotelRequest;
import cl.tripplanner.turismo.hoteles.dto.HotelResponse;
import cl.tripplanner.turismo.hoteles.exception.ResourceNotFoundException;
import cl.tripplanner.turismo.hoteles.kafka.HotelEvent;
import cl.tripplanner.turismo.hoteles.kafka.HotelEventProducer;
import cl.tripplanner.turismo.hoteles.mapper.HotelMapper;
import cl.tripplanner.turismo.hoteles.modelo.Ciudad;
import cl.tripplanner.turismo.hoteles.modelo.Hotel;
import cl.tripplanner.turismo.hoteles.repository.CiudadRepository;
import cl.tripplanner.turismo.hoteles.repository.HotelRepository;
import cl.tripplanner.turismo.hoteles.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository repository;
    private final CiudadRepository ciudadRepository;
    private final HotelEventProducer producer;

    @Override
    public List<HotelResponse> listar() {
        return repository.findAll()
                .stream()
                .map(HotelMapper::toDTO)
                .toList();
    }

    @Override
    public HotelResponse obtener(Integer id) {

        Integer hotelId = Objects.requireNonNull(
                id,
                "El id no puede ser nulo"
        );

        Hotel hotel = repository.findById(hotelId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Hotel no encontrado"));

        return HotelMapper.toDTO(hotel);
    }

    @Override
    public HotelResponse crear(HotelRequest dto) {

        Objects.requireNonNull(
                dto,
                "La solicitud no puede ser nula"
        );

        Integer ciudadId = Objects.requireNonNull(
                dto.getCiudadId(),
                "El id de la ciudad no puede ser nulo"
        );

        Ciudad ciudad = ciudadRepository.findById(ciudadId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Ciudad no encontrada"));

        Hotel hotel = Objects.requireNonNull(
                HotelMapper.toEntity(dto),
                "El hotel no puede ser nulo"
        );

        hotel.setCiudad(ciudad);

        Hotel hotelGuardado = repository.save(hotel);

        producer.enviarHotelCreado(
                HotelEvent.builder()
                        .id(hotelGuardado.getId())
                        .nombre(hotelGuardado.getNombre())
                        .estrellas(hotelGuardado.getEstrellas())
                        .accion("CREADO")
                        .build()
        );

        return HotelMapper.toDTO(hotelGuardado);
    }

    @Override
    public void eliminar(Integer id) {

        Integer hotelId = Objects.requireNonNull(
                id,
                "El id no puede ser nulo"
        );

        Hotel hotel = repository.findById(hotelId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Hotel no encontrado"));

        producer.enviarHotelEliminado(
                HotelEvent.builder()
                        .id(hotel.getId())
                        .nombre(hotel.getNombre())
                        .estrellas(hotel.getEstrellas())
                        .accion("ELIMINADO")
                        .build()
        );

        repository.delete(hotel);
    }
}