package cl.tripplanner.turismo.destinos.service;

import cl.tripplanner.turismo.destinos.dto.AtraccionRequest;
import cl.tripplanner.turismo.destinos.dto.AtraccionResponse;

import java.util.List;

public interface AtraccionService {

    List<AtraccionResponse> listar();

    AtraccionResponse obtener(Integer id);

    AtraccionResponse crear(AtraccionRequest dto);

    void eliminar(Integer id);
}