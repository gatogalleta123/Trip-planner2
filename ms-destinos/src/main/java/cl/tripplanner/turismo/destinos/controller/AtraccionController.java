package cl.tripplanner.turismo.destinos.controller;

import cl.tripplanner.turismo.destinos.dto.*;
import cl.tripplanner.turismo.destinos.service.AtraccionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atracciones")
@RequiredArgsConstructor
public class AtraccionController {

    private final AtraccionService service;

    @GetMapping
    public List<AtraccionResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public AtraccionResponse obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }

@PostMapping
public AtraccionResponse crear(@Valid @RequestBody AtraccionRequest dto) {
    return service.crear(dto);
}

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}