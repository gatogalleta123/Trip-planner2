package cl.tripplanner.turismo.destinos.service;

import cl.tripplanner.turismo.destinos.dto.DestinoRequest;
import cl.tripplanner.turismo.destinos.dto.DestinoResponse;

import java.util.List;

public interface DestinoService {

    List<DestinoResponse> listar();

    DestinoResponse obtener(Integer id);

    DestinoResponse crear(DestinoRequest dto);

    void eliminar(Integer id);

}
