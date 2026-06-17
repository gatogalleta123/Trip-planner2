package cl.tripplanner.turismo.destinos.controller;

import cl.tripplanner.turismo.destinos.dto.*;
import cl.tripplanner.turismo.destinos.service.PaisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/paises")
@RequiredArgsConstructor
public class PaisController {

    private final PaisService service;

    @GetMapping
    public List<PaisResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public PaisResponse obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }

@PostMapping
public PaisResponse crear(@Valid @RequestBody PaisRequest dto) {
    return service.crear(dto);
}

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}