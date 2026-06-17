package cl.tripplanner.turismo.hoteles.controller;

import cl.tripplanner.turismo.hoteles.dto.*;
import cl.tripplanner.turismo.hoteles.service.CiudadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ciudades")
@RequiredArgsConstructor
public class CiudadController {

    private final CiudadService service;

    @GetMapping
    public List<CiudadResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public CiudadResponse obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }

    @PostMapping
    public CiudadResponse crear(@Valid @RequestBody CiudadRequest dto) {
        return service.crear(dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}