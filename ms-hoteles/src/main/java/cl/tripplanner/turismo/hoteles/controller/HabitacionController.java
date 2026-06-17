package cl.tripplanner.turismo.hoteles.controller;

import cl.tripplanner.turismo.hoteles.dto.*;
import cl.tripplanner.turismo.hoteles.service.HabitacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habitaciones")
@RequiredArgsConstructor
public class HabitacionController {

    private final HabitacionService service;

    @GetMapping
    public List<HabitacionResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public HabitacionResponse obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }

    @PostMapping
    public HabitacionResponse crear(@Valid @RequestBody HabitacionRequest dto) {
        return service.crear(dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}