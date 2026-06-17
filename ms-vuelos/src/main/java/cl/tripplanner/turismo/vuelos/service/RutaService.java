package cl.tripplanner.turismo.vuelos.service;

import cl.tripplanner.turismo.vuelos.dto.RutaRequest;
import cl.tripplanner.turismo.vuelos.dto.RutaResponse;

import java.util.List;

public interface RutaService {
    RutaResponse crear(RutaRequest request);
    List<RutaResponse> listar();
    RutaResponse obtenerPorId(Integer id);
    RutaResponse actualizar(Integer id, RutaRequest request);
    void eliminar(Integer id);
}