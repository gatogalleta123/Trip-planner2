package cl.tripplanner.turismo.vuelos.controller;

import cl.tripplanner.turismo.vuelos.dto.AerolineaRequest;
import cl.tripplanner.turismo.vuelos.service.AerolineaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/aerolineas")
@RequiredArgsConstructor
public class AerolineaController {

    private final AerolineaService service;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody AerolineaRequest request) {
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

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody AerolineaRequest request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}