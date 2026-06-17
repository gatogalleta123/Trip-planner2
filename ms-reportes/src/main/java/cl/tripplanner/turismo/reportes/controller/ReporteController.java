package cl.tripplanner.turismo.reportes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.tripplanner.turismo.reportes.dto.HistorialReporteResponse;
import cl.tripplanner.turismo.reportes.dto.ReporteRequest;
import cl.tripplanner.turismo.reportes.dto.ReporteResponse;
import cl.tripplanner.turismo.reportes.service.ReporteService;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping
    public ResponseEntity<List<ReporteResponse>>
    findAll() {

        return ResponseEntity.ok(
                reporteService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteResponse>
    findById(
            @PathVariable @NonNull Long id
    ) {

        return ResponseEntity.ok(
                reporteService.findById(id)
        );
    }

    // Ejemplo:
    // GET /api/v1/reportes/activos?activo=true
    @GetMapping("/activos")
    public ResponseEntity<List<ReporteResponse>>
    findByActivo(
            @RequestParam(defaultValue = "true")
            Boolean activo
    ) {

        return ResponseEntity.ok(
                reporteService.findByActivo(activo)
        );
    }

    @PostMapping
    public ResponseEntity<ReporteResponse>
    create(
            @Valid @RequestBody
            ReporteRequest request
    ) {

        ReporteResponse creado =
                reporteService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReporteResponse>
    update(
            @PathVariable @NonNull Long id,
            @Valid @RequestBody
            ReporteRequest request
    ) {

        return ResponseEntity.ok(
                reporteService.update(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteById(
            @PathVariable @NonNull Long id
    ) {

        reporteService.deleteById(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    // Ejecuta un reporte y registra
    // una ejecución en historial.
    @PostMapping("/{id}/ejecutar")
    public ResponseEntity<HistorialReporteResponse>
    ejecutarReporte(
            @PathVariable @NonNull Long id
    ) {

        return ResponseEntity.ok(
                reporteService.ejecutarReporte(id)
        );
    }
}