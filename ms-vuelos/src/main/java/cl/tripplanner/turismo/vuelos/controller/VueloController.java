package cl.tripplanner.turismo.vuelos.controller;

import cl.tripplanner.turismo.vuelos.dto.VueloRequest;
import cl.tripplanner.turismo.vuelos.service.VueloService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vuelos")
@RequiredArgsConstructor
public class VueloController {

    private final VueloService service;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody VueloRequest request) {
        return ResponseEntity.ok(service.crear(request));
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}