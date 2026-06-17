package cl.tripplanner.turismo.reportes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.tripplanner.common.exception.EntityNotFoundException;
import cl.tripplanner.turismo.reportes.dto.ReservaProyeccionResponse;
import cl.tripplanner.turismo.reportes.mapper.ReservaProyeccionMapper;
import cl.tripplanner.turismo.reportes.model.ReservaProyeccion;
import cl.tripplanner.turismo.reportes.repository.ReservaProyeccionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservaProyeccionService {
    private final ReservaProyeccionRepository reservaProyeccionRepository;
    private final ReservaProyeccionMapper reservaProyeccionMapper;

    @Transactional
    public List<ReservaProyeccionResponse> findAll() {
        return reservaProyeccionMapper.toResponseList(reservaProyeccionRepository.findAll());
    }

    @Transactional
    public void save(String id, String clienteId, String estado) {
        ReservaProyeccion reservaProyeccion = new ReservaProyeccion();
        reservaProyeccion.setId(id);
        reservaProyeccion.setClienteId(clienteId);
        reservaProyeccion.setEstado(estado);
        reservaProyeccionRepository.save(reservaProyeccion);
    }

    @Transactional
    public void deleteById(String id) {
        ReservaProyeccion reservaProyeccion = reservaProyeccionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ReservaProyeccion", "id", id));
        reservaProyeccionRepository.delete(reservaProyeccion);
    }

    @Transactional
    public void update(String id, String clienteId, String estado) {
        ReservaProyeccion reservaProyeccion = reservaProyeccionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ReservaProyeccion", "id", id));
        reservaProyeccion.setId(id);
        reservaProyeccion.setClienteId(clienteId);
        reservaProyeccion.setEstado(estado);
        reservaProyeccionRepository.save(reservaProyeccion);
    }
}
