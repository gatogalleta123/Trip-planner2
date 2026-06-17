package cl.tripplanner.turismo.hoteles.service.impl;

import cl.tripplanner.turismo.hoteles.dto.CiudadRequest;
import cl.tripplanner.turismo.hoteles.dto.CiudadResponse;
import cl.tripplanner.turismo.hoteles.exception.ResourceNotFoundException;
import cl.tripplanner.turismo.hoteles.kafka.CiudadEvent;
import cl.tripplanner.turismo.hoteles.kafka.CiudadEventProducer;
import cl.tripplanner.turismo.hoteles.mapper.CiudadMapper;
import cl.tripplanner.turismo.hoteles.modelo.Ciudad;
import cl.tripplanner.turismo.hoteles.repository.CiudadRepository;
import cl.tripplanner.turismo.hoteles.service.CiudadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CiudadServiceImpl implements CiudadService {

    private final CiudadRepository repository;
    private final CiudadEventProducer producer;

    @Override
    public List<CiudadResponse> listar() {
        return repository.findAll()
                .stream()
                .map(CiudadMapper::toDTO)
                .toList();
    }

    @Override
    public CiudadResponse obtener(Integer id) {

        Integer ciudadId = Objects.requireNonNull(
                id,
                "El id no puede ser nulo"
        );

        Ciudad ciudad = repository.findById(ciudadId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Ciudad no encontrada"));

        return CiudadMapper.toDTO(ciudad);
    }

    @Override
    public CiudadResponse crear(CiudadRequest dto) {

        Ciudad ciudad = Objects.requireNonNull(
                CiudadMapper.toEntity(dto),
                "La ciudad no puede ser nula"
        );

        Ciudad guardada = repository.save(ciudad);

        producer.enviarCiudadCreada(
                CiudadEvent.builder()
                        .id(guardada.getId())
                        .nombre(guardada.getNombre())
                        .pais(guardada.getPais())
                        .activo(guardada.getActivo())
                        .accion("CREADO")
                        .build()
        );

        return CiudadMapper.toDTO(guardada);
    }

    @Override
    public void eliminar(Integer id) {

        Integer ciudadId = Objects.requireNonNull(
                id,
                "El id no puede ser nulo"
        );

        Ciudad ciudad = repository.findById(ciudadId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Ciudad no encontrada"));

        producer.enviarCiudadEliminada(
                CiudadEvent.builder()
                        .id(ciudad.getId())
                        .nombre(ciudad.getNombre())
                        .pais(ciudad.getPais())
                        .activo(ciudad.getActivo())
                        .accion("ELIMINADO")
                        .build()
        );

        repository.delete(ciudad);
    }
}