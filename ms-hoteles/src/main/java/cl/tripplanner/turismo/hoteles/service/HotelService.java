package cl.tripplanner.turismo.hoteles.service;

import cl.tripplanner.turismo.hoteles.dto.*;
import java.util.List;

public interface HotelService {
    List<HotelResponse> listar();
    HotelResponse obtener(Integer id);
    HotelResponse crear(HotelRequest dto);
    void eliminar(Integer id);
}