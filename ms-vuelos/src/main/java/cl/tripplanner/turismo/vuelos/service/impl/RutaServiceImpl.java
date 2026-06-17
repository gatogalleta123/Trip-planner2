package cl.tripplanner.turismo.vuelos.service.impl;

import cl.tripplanner.turismo.vuelos.dto.RutaRequest;
import cl.tripplanner.turismo.vuelos.dto.RutaResponse;
import cl.tripplanner.turismo.vuelos.kafka.RutaEvent;
import cl.tripplanner.turismo.vuelos.kafka.RutaEventProducer;
import cl.tripplanner.turismo.vuelos.mapper.RutaMapper;
import cl.tripplanner.turismo.vuelos.modelo.Ruta;
import cl.tripplanner.turismo.vuelos.repository.RutaRepository;
import cl.tripplanner.turismo.vuelos.service.RutaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RutaServiceImpl implements RutaService {

    private final RutaRepository repo;
    private final RutaEventProducer producer;

    @Override
    public RutaResponse crear(RutaRequest request) {

        Ruta ruta = Objects.requireNonNull(
                RutaMapper.toEntity(request),
                "La ruta no puede ser nula"
        );

        Ruta guardada = repo.save(ruta);

        producer.enviarRutaCreada(
                RutaEvent.builder()
                        .id(guardada.getId())
                        .origen(guardada.getOrigen())
                        .destino(guardada.getDestino())
                        .distanciaKm(guardada.getDistanciaKm())
                        .activo(guardada.getActivo())
                        .accion("CREADO")
                        .build()
        );

        return RutaMapper.toResponse(guardada);
    }

    @Override
    public List<RutaResponse> listar() {
        return repo.findAll()
                .stream()
                .map(RutaMapper::toResponse)
                .toList();
    }

    @Override
    public RutaResponse obtenerPorId(Integer id) {

        Integer rutaId = Objects.requireNonNull(
                id,
                "El id no puede ser nulo"
        );

        Ruta ruta = repo.findById(rutaId)
                .orElseThrow(() ->
                        new RuntimeException("Ruta no encontrada"));

        return RutaMapper.toResponse(ruta);
    }

    @Override
    public RutaResponse actualizar(Integer id, RutaRequest request) {

        Integer rutaId = Objects.requireNonNull(
                id,
                "El id no puede ser nulo"
        );

        Ruta ruta = repo.findById(rutaId)
                .orElseThrow(() ->
                        new RuntimeException("Ruta no encontrada"));

        ruta.setOrigen(request.getOrigen());
        ruta.setDestino(request.getDestino());
        ruta.setDistanciaKm(request.getDistanciaKm());
        ruta.setActivo(request.getActivo());

        return RutaMapper.toResponse(
                repo.save(ruta)
        );
    }

    @Override
    public void eliminar(Integer id) {

        Integer rutaId = Objects.requireNonNull(
                id,
                "El id no puede ser nulo"
        );

        Ruta ruta = repo.findById(rutaId)
                .orElseThrow(() ->
                        new RuntimeException("Ruta no encontrada"));

        producer.enviarRutaEliminada(
                RutaEvent.builder()
                        .id(ruta.getId())
                        .origen(ruta.getOrigen())
                        .destino(ruta.getDestino())
                        .distanciaKm(ruta.getDistanciaKm())
                        .activo(ruta.getActivo())
                        .accion("ELIMINADO")
                        .build()
        );

        repo.delete(ruta);
    }
}