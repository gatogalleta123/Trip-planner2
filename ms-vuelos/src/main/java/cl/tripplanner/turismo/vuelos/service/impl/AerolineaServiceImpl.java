package cl.tripplanner.turismo.vuelos.service.impl;

import cl.tripplanner.turismo.vuelos.dto.AerolineaRequest;
import cl.tripplanner.turismo.vuelos.dto.AerolineaResponse;
import cl.tripplanner.turismo.vuelos.kafka.AerolineaEvent;
import cl.tripplanner.turismo.vuelos.kafka.AerolineaEventProducer;
import cl.tripplanner.turismo.vuelos.mapper.AerolineaMapper;
import cl.tripplanner.turismo.vuelos.modelo.Aerolinea;
import cl.tripplanner.turismo.vuelos.repository.AerolineaRepository;
import cl.tripplanner.turismo.vuelos.service.AerolineaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AerolineaServiceImpl implements AerolineaService {

    private final AerolineaRepository repo;
    private final AerolineaEventProducer producer;

    @Override
    public AerolineaResponse crear(AerolineaRequest request) {

        Aerolinea aerolinea = Objects.requireNonNull(
                AerolineaMapper.toEntity(request),
                "La aerolínea no puede ser nula"
        );

        Aerolinea guardada = repo.save(aerolinea);

        producer.enviarAerolineaCreada(
                AerolineaEvent.builder()
                        .id(guardada.getId())
                        .nombre(guardada.getNombre())
                        .codigoIata(guardada.getCodigoIata())
                        .activo(guardada.getActivo())
                        .accion("CREADO")
                        .build()
        );

        return AerolineaMapper.toResponse(guardada);
    }

    @Override
    public List<AerolineaResponse> listar() {
        return repo.findAll()
                .stream()
                .map(AerolineaMapper::toResponse)
                .toList();
    }

    @Override
    public AerolineaResponse obtenerPorId(Integer id) {

        Integer aerolineaId = Objects.requireNonNull(
                id,
                "El id no puede ser nulo"
        );

        Aerolinea aerolinea = repo.findById(aerolineaId)
                .orElseThrow(() ->
                        new RuntimeException("Aerolinea no encontrada"));

        return AerolineaMapper.toResponse(aerolinea);
    }

    @Override
    public AerolineaResponse actualizar(Integer id, AerolineaRequest request) {

        Integer aerolineaId = Objects.requireNonNull(
                id,
                "El id no puede ser nulo"
        );

        Aerolinea aerolinea = repo.findById(aerolineaId)
                .orElseThrow(() ->
                        new RuntimeException("Aerolinea no encontrada"));

        aerolinea.setNombre(request.getNombre());
        aerolinea.setCodigoIata(request.getCodigoIata());
        aerolinea.setActivo(request.getActivo());

        return AerolineaMapper.toResponse(
                repo.save(aerolinea)
        );
    }

    @Override
    public void eliminar(Integer id) {

        Integer aerolineaId = Objects.requireNonNull(
                id,
                "El id no puede ser nulo"
        );

        Aerolinea aerolinea = repo.findById(aerolineaId)
                .orElseThrow(() ->
                        new RuntimeException("Aerolinea no encontrada"));

        producer.enviarAerolineaEliminada(
                AerolineaEvent.builder()
                        .id(aerolinea.getId())
                        .nombre(aerolinea.getNombre())
                        .codigoIata(aerolinea.getCodigoIata())
                        .activo(aerolinea.getActivo())
                        .accion("ELIMINADO")
                        .build()
        );

        repo.delete(aerolinea);
    }
}