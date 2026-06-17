package cl.tripplanner.turismo.vuelos.service;

import cl.tripplanner.turismo.vuelos.dto.AerolineaRequest;
import cl.tripplanner.turismo.vuelos.dto.AerolineaResponse;

import java.util.List;

public interface AerolineaService {
    AerolineaResponse crear(AerolineaRequest request);
    List<AerolineaResponse> listar();
    AerolineaResponse obtenerPorId(Integer id);
    AerolineaResponse actualizar(Integer id, AerolineaRequest request);
    void eliminar(Integer id);
}