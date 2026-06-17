package cl.tripplanner.turismo.destinos.service.impl;

import cl.tripplanner.turismo.destinos.dto.PaisRequest;
import cl.tripplanner.turismo.destinos.dto.PaisResponse;
import cl.tripplanner.turismo.destinos.exception.ResourceNotFoundException;
import cl.tripplanner.turismo.destinos.kafka.PaisEvent;
import cl.tripplanner.turismo.destinos.kafka.PaisEventProducer;
import cl.tripplanner.turismo.destinos.mapper.PaisMapper;
import cl.tripplanner.turismo.destinos.modelo.Pais;
import cl.tripplanner.turismo.destinos.repository.PaisRepository;
import cl.tripplanner.turismo.destinos.service.PaisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaisServiceImpl implements PaisService {

    private final PaisRepository repository;
    private final PaisEventProducer producer;

    @Override
    public List<PaisResponse> listar() {
        return repository.findAll()
                .stream()
                .map(PaisMapper::toDTO)
                .toList();
    }

    @Override
    public PaisResponse obtener(Integer id) {

        Integer paisId = Objects.requireNonNull(
                id,
                "El id no puede ser nulo"
        );

        Pais pais = Objects.requireNonNull(
                repository.findById(paisId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Pais no encontrado"))
        );

        return PaisMapper.toDTO(pais);
    }

    @Override
    public PaisResponse crear(PaisRequest dto) {

        Pais pais = Objects.requireNonNull(
                PaisMapper.toEntity(dto),
                "El pais no puede ser nulo"
        );

        Pais guardado = repository.save(pais);

        producer.enviarPaisCreado(
                PaisEvent.builder()
                        .id(guardado.getId())
                        .nombre(guardado.getNombre())
                        .codigoIso(guardado.getCodigoIso())
                        .activo(guardado.getActivo())
                        .accion("CREADO")
                        .build()
        );

        return PaisMapper.toDTO(guardado);
    }

    @Override
    public void eliminar(Integer id) {

        Integer paisId = Objects.requireNonNull(
                id,
                "El id no puede ser nulo"
        );

        Pais pais = Objects.requireNonNull(
                repository.findById(paisId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Pais no encontrado"))
        );

        producer.enviarPaisEliminado(
                PaisEvent.builder()
                        .id(pais.getId())
                        .nombre(pais.getNombre())
                        .codigoIso(pais.getCodigoIso())
                        .activo(pais.getActivo())
                        .accion("ELIMINADO")
                        .build()
        );

        repository.delete(pais);
    }
}