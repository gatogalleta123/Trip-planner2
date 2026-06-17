package cl.tripplanner.turismo.destinos.service.impl;

import cl.tripplanner.turismo.destinos.dto.AtraccionRequest;
import cl.tripplanner.turismo.destinos.dto.AtraccionResponse;
import cl.tripplanner.turismo.destinos.exception.ResourceNotFoundException;
import cl.tripplanner.turismo.destinos.kafka.AtraccionEvent;
import cl.tripplanner.turismo.destinos.kafka.AtraccionEventProducer;
import cl.tripplanner.turismo.destinos.mapper.AtraccionMapper;
import cl.tripplanner.turismo.destinos.modelo.Atraccion;
import cl.tripplanner.turismo.destinos.modelo.Destino;
import cl.tripplanner.turismo.destinos.repository.AtraccionRepository;
import cl.tripplanner.turismo.destinos.repository.DestinoRepository;
import cl.tripplanner.turismo.destinos.service.AtraccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AtraccionServiceImpl implements AtraccionService {

    private final AtraccionRepository repository;
    private final DestinoRepository destinoRepository;
    private final AtraccionEventProducer producer;

    @Override
    public List<AtraccionResponse> listar() {

        return repository.findAll()
                .stream()
                .map(AtraccionMapper::toDTO)
                .toList();
    }

    @Override
    public AtraccionResponse obtener(Integer id) {

        Objects.requireNonNull(id, "El id no puede ser nulo");

        Atraccion atraccion = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Atracción no encontrada"));

        return AtraccionMapper.toDTO(atraccion);
    }

    @Override
    public AtraccionResponse crear(AtraccionRequest dto) {

        if (dto == null) {
            throw new IllegalArgumentException(
                    "La solicitud no puede ser nula");
        }

        Integer destinoId = dto.getDestinoId();

        if (destinoId == null) {
            throw new ResourceNotFoundException(
                    "Debe indicar un destino");
        }

        Destino destino = destinoRepository.findById(destinoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Destino no encontrado"));

        Atraccion atraccion = AtraccionMapper.toEntity(dto);
        atraccion.setDestino(destino);

        Atraccion atraccionGuardada = repository.save(atraccion);

        producer.enviarAtraccionCreada(
                AtraccionEvent.builder()
                        .id(atraccionGuardada.getId())
                        .nombre(atraccionGuardada.getNombre())
                        .precio(atraccionGuardada.getPrecio())
                        .disponible(atraccionGuardada.getDisponible())
                        .destinoNombre(destino.getNombre())
                        .accion("CREADO")
                        .build()
        );

        return AtraccionMapper.toDTO(atraccionGuardada);
    }

    @Override
    public void eliminar(Integer id) {

        Objects.requireNonNull(id, "El id no puede ser nulo");

        Atraccion atraccion = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Atracción no encontrada"));

        producer.enviarAtraccionEliminada(
                AtraccionEvent.builder()
                        .id(atraccion.getId())
                        .nombre(atraccion.getNombre())
                        .precio(atraccion.getPrecio())
                        .disponible(atraccion.getDisponible())
                        .destinoNombre(
                                atraccion.getDestino() != null
                                        ? atraccion.getDestino().getNombre()
                                        : null
                        )
                        .accion("ELIMINADO")
                        .build()
        );

        repository.delete(atraccion);
    }
}