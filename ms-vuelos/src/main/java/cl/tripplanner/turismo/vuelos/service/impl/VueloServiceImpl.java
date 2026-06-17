package cl.tripplanner.turismo.vuelos.service.impl;

import cl.tripplanner.turismo.vuelos.dto.VueloRequest;
import cl.tripplanner.turismo.vuelos.dto.VueloResponse;
import cl.tripplanner.turismo.vuelos.feign.DestinoFeignClient;
import cl.tripplanner.turismo.vuelos.feign.HotelFeignClient;
import cl.tripplanner.turismo.vuelos.kafka.VueloEvent;
import cl.tripplanner.turismo.vuelos.kafka.VueloEventProducer;
import cl.tripplanner.turismo.vuelos.mapper.VueloMapper;
import cl.tripplanner.turismo.vuelos.modelo.Aerolinea;
import cl.tripplanner.turismo.vuelos.modelo.Ruta;
import cl.tripplanner.turismo.vuelos.modelo.Vuelo;
import cl.tripplanner.turismo.vuelos.repository.AerolineaRepository;
import cl.tripplanner.turismo.vuelos.repository.RutaRepository;
import cl.tripplanner.turismo.vuelos.repository.VueloRepository;
import cl.tripplanner.turismo.vuelos.service.VueloService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VueloServiceImpl implements VueloService {

    private final VueloRepository repo;
    private final AerolineaRepository aerolineaRepo;
    private final RutaRepository rutaRepo;
    private final VueloEventProducer producer;

    private final HotelFeignClient hotelFeignClient;
    private final DestinoFeignClient destinoFeignClient;

    @Override
    public VueloResponse crear(VueloRequest request) {

        Integer aerolineaId = Objects.requireNonNull(
                request.getAerolineaId(),
                "El id de la aerolínea no puede ser nulo"
        );

        Integer rutaId = Objects.requireNonNull(
                request.getRutaId(),
                "El id de la ruta no puede ser nulo"
        );

        // Llamadas Feign (demostración de comunicación entre microservicios)
        hotelFeignClient.obtenerHotel(1);
        destinoFeignClient.obtenerDestino(1);

        Aerolinea aerolinea = aerolineaRepo.findById(aerolineaId)
                .orElseThrow(() ->
                        new RuntimeException("Aerolinea no encontrada"));

        Ruta ruta = rutaRepo.findById(rutaId)
                .orElseThrow(() ->
                        new RuntimeException("Ruta no encontrada"));

        Vuelo vuelo = Objects.requireNonNull(
                Vuelo.builder()
                        .aerolinea(aerolinea)
                        .ruta(ruta)
                        .fechaSalida(LocalDateTime.parse(request.getFechaSalida()))
                        .precio(new BigDecimal(request.getPrecio()))
                        .build(),
                "El vuelo no puede ser nulo"
        );

        Vuelo guardado = repo.save(vuelo);

        producer.enviarVueloCreado(
                VueloEvent.builder()
                        .id(guardado.getId())
                        .aerolineaNombre(aerolinea.getNombre())
                        .origen(ruta.getOrigen())
                        .destino(ruta.getDestino())
                        .fechaSalida(guardado.getFechaSalida().toString())
                        .precio(guardado.getPrecio().toString())
                        .accion("CREADO")
                        .build()
        );

        return VueloMapper.toResponse(guardado);
    }

    @Override
    public List<VueloResponse> listar() {
        return repo.findAll()
                .stream()
                .map(VueloMapper::toResponse)
                .toList();
    }

    @Override
    public VueloResponse obtenerPorId(Integer id) {

        Integer vueloId = Objects.requireNonNull(
                id,
                "El id no puede ser nulo"
        );

        Vuelo vuelo = repo.findById(vueloId)
                .orElseThrow(() ->
                        new RuntimeException("Vuelo no encontrado"));

        return VueloMapper.toResponse(vuelo);
    }

    @Override
    public void eliminar(Integer id) {

        Integer vueloId = Objects.requireNonNull(
                id,
                "El id no puede ser nulo"
        );

        Vuelo vuelo = repo.findById(vueloId)
                .orElseThrow(() ->
                        new RuntimeException("Vuelo no encontrado"));

        producer.enviarVueloEliminado(
                VueloEvent.builder()
                        .id(vuelo.getId())
                        .aerolineaNombre(vuelo.getAerolinea().getNombre())
                        .origen(vuelo.getRuta().getOrigen())
                        .destino(vuelo.getRuta().getDestino())
                        .fechaSalida(vuelo.getFechaSalida().toString())
                        .precio(vuelo.getPrecio().toString())
                        .accion("ELIMINADO")
                        .build()
        );

        repo.delete(vuelo);
    }
}