package cl.tripplanner.turismo.hoteles.service;

import cl.tripplanner.turismo.hoteles.dto.*;
import java.util.List;

public interface CiudadService {

    List<CiudadResponse> listar();

    CiudadResponse obtener(Integer id);

    CiudadResponse crear(CiudadRequest dto);

    void eliminar(Integer id);
}