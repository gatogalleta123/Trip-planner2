package cl.tripplanner.turismo.destinos.controller;

import cl.tripplanner.turismo.destinos.dto.*;
import cl.tripplanner.turismo.destinos.service.DestinoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinos")
@RequiredArgsConstructor
public class DestinoController {

    private final DestinoService service;

    @GetMapping
    public List<DestinoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public DestinoResponse obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }

@PostMapping
public DestinoResponse crear(@Valid @RequestBody DestinoRequest dto) {
    return service.crear(dto);
}

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}