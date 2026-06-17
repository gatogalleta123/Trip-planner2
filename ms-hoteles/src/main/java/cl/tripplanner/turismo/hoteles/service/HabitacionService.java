package cl.tripplanner.turismo.hoteles.service;

import cl.tripplanner.turismo.hoteles.dto.*;
import java.util.List;

public interface HabitacionService {
    List<HabitacionResponse> listar();
    HabitacionResponse obtener(Integer id);
    HabitacionResponse crear(HabitacionRequest dto);
    void eliminar(Integer id);
}