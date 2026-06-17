package cl.tripplanner.turismo.notificaciones.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.tripplanner.turismo.notificaciones.dto.UsuarioProyeccionResponse;
import cl.tripplanner.turismo.notificaciones.service.UsuarioProyeccionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/usuarios-proyeccion")
public class UsuarioProyeccionController {
    private final UsuarioProyeccionService usuarioProyeccionService;

    @GetMapping
    public ResponseEntity<List<UsuarioProyeccionResponse>> findAll() {
        return ResponseEntity.ok(usuarioProyeccionService.findAll());
    }
}
