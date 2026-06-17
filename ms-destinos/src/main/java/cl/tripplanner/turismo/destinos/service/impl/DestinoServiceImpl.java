package cl.tripplanner.turismo.destinos.service.impl;

import cl.tripplanner.turismo.destinos.dto.DestinoRequest;
import cl.tripplanner.turismo.destinos.dto.DestinoResponse;
import cl.tripplanner.turismo.destinos.exception.ResourceNotFoundException;
import cl.tripplanner.turismo.destinos.kafka.DestinoEvent;
import cl.tripplanner.turismo.destinos.kafka.DestinoEventProducer;
import cl.tripplanner.turismo.destinos.mapper.DestinoMapper;
import cl.tripplanner.turismo.destinos.modelo.Destino;
import cl.tripplanner.turismo.destinos.repository.DestinoRepository;
import cl.tripplanner.turismo.destinos.service.DestinoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DestinoServiceImpl implements DestinoService {

    private final DestinoRepository repository;
private final DestinoEventProducer producer;
    @Override
    public List<DestinoResponse> listar() {
        return repository.findAll()
                .stream()
                .map(DestinoMapper::toDTO)
                .toList();
    }

@Override
public DestinoResponse obtener(Integer id) {

    Integer destinoId = Objects.requireNonNull(
            id,
            "El id no puede ser nulo"
    );

    Destino destino = Objects.requireNonNull(
            repository.findById(destinoId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Destino no encontrado"))
    );

    return DestinoMapper.toDTO(destino);
}

@Override
public DestinoResponse crear(DestinoRequest dto) {

    Destino destino = Objects.requireNonNull(
            DestinoMapper.toEntity(dto),
            "El destino no puede ser nulo"
    );

    Destino guardado = repository.save(destino);

    producer.enviarDestinoCreado(
            DestinoEvent.builder()
                    .id(guardado.getId())
                    .nombre(guardado.getNombre())
                    .tipo(guardado.getTipo())
                    .activo(guardado.getActivo())
                    .accion("CREADO")
                    .build()
    );

    return DestinoMapper.toDTO(guardado);
}

@Override
public void eliminar(Integer id) {

    Integer destinoId = Objects.requireNonNull(
            id,
            "El id no puede ser nulo"
    );

    Destino destino = Objects.requireNonNull(
            repository.findById(destinoId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Destino no encontrado"))
    );

    producer.enviarDestinoEliminado(
            DestinoEvent.builder()
                    .id(destino.getId())
                    .nombre(destino.getNombre())
                    .tipo(destino.getTipo())
                    .activo(destino.getActivo())
                    .accion("ELIMINADO")
                    .build()
    );

    repository.delete(destino);
}
}