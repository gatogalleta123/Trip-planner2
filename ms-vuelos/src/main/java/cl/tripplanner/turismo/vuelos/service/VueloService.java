package cl.tripplanner.turismo.vuelos.service;

import cl.tripplanner.turismo.vuelos.dto.VueloRequest;
import cl.tripplanner.turismo.vuelos.dto.VueloResponse;

import java.util.List;

public interface VueloService {
    VueloResponse crear(VueloRequest request);
    List<VueloResponse> listar();
    VueloResponse obtenerPorId(Integer id);
    void eliminar(Integer id);
}