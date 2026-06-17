package cl.tripplanner.turismo.destinos.service;

import cl.tripplanner.turismo.destinos.dto.PaisRequest;
import cl.tripplanner.turismo.destinos.dto.PaisResponse;

import java.util.List;

public interface PaisService {

    List<PaisResponse> listar();

    PaisResponse obtener(Integer id);

    PaisResponse crear(PaisRequest dto);

    void eliminar(Integer id);
}
