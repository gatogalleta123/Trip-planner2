package cl.tripplanner.turismo.usuarios.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.tripplanner.turismo.usuarios.dto.OrganizacionRequest;
import cl.tripplanner.turismo.usuarios.dto.OrganizacionResponse;
import cl.tripplanner.turismo.usuarios.dto.RolRequest;
import cl.tripplanner.turismo.usuarios.dto.RolResponse;
import cl.tripplanner.turismo.usuarios.dto.UsuarioRequest;
import cl.tripplanner.turismo.usuarios.dto.UsuarioResponse;
import cl.tripplanner.turismo.usuarios.dto.UsuarioUpdateRequest;
import cl.tripplanner.turismo.usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }
    
    @GetMapping("/{email}")
    public ResponseEntity<UsuarioResponse> findByEmail(@PathVariable @NonNull String email) {
        return ResponseEntity.ok(usuarioService.findByEmail(email));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> create(@Valid @RequestBody UsuarioRequest request) {
        UsuarioResponse creado = usuarioService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
    
    @PutMapping("/{email}")
    public ResponseEntity<UsuarioResponse> update(
            @PathVariable @NonNull String email,
            @Valid @RequestBody UsuarioUpdateRequest request) {
        return ResponseEntity.ok(usuarioService.update(email, request));
    }
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteById(@PathVariable @NonNull String email) {
        usuarioService.deleteByEmail(email);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/existe/email/{email}")
    public ResponseEntity<Boolean> existByEmail(@PathVariable String email) {
        return ResponseEntity.ok(usuarioService.existsByEmail(email));
    }
    
    //ORGANIZACIONES
    @GetMapping("/organizaciones")
    public ResponseEntity<List<OrganizacionResponse>>
    findAllOrganizaciones() {

        return ResponseEntity.ok(
                usuarioService.findAllOrganizaciones());
    }

    @GetMapping("/organizaciones/{id}")
    public ResponseEntity<OrganizacionResponse>
    findOrganizacionById(
        @PathVariable Integer id) {

    return ResponseEntity.ok(
            usuarioService.findOrganizacionById(id));
    }

    @PostMapping("/organizaciones")
    public ResponseEntity<OrganizacionResponse>
    createOrganizacion(
        @Valid @RequestBody OrganizacionRequest request) {

    OrganizacionResponse creada =
            usuarioService.createOrganizacion(request);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(creada);
    }

    @PutMapping("/organizaciones/{id}")
    public ResponseEntity<OrganizacionResponse>
    updateOrganizacion(
        @PathVariable Integer id,
        @Valid @RequestBody OrganizacionRequest request) {

    return ResponseEntity.ok(
            usuarioService.updateOrganizacion(
                    id,
                    request));
    }

    @DeleteMapping("/organizaciones/{id}")
    public ResponseEntity<Void>
    deleteOrganizacion(
        @PathVariable Integer id) {

    usuarioService.deleteOrganizacion(id);

    return ResponseEntity.noContent().build();
    }

    @GetMapping("/organizaciones/existe/{id}")
    public ResponseEntity<Boolean>
    existsOrganizacion(
        @PathVariable Integer id) {

    return ResponseEntity.ok(
            usuarioService.existsOrganizacionById(id));
    }

    //ROLES
    @GetMapping("/roles")
    public ResponseEntity<List<RolResponse>>
    findAllRoles() {

        return ResponseEntity.ok(
                usuarioService.findAllRoles());
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<RolResponse>
    findRolById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                usuarioService.findRolById(id));
    }

    @PostMapping("/roles")
    public ResponseEntity<RolResponse>
    createRol(
        @Valid @RequestBody RolRequest request) {

    RolResponse creada =
            usuarioService.createRol(request);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(creada);
    }


    @PutMapping("/roles/{id}")
    public ResponseEntity<RolResponse>
    updateRol(
            @PathVariable Integer id,
            @Valid @RequestBody RolRequest request) {

        return ResponseEntity.ok(
                usuarioService.updateRol(
                        id,
                        request));
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void>
    deleteRol(
            @PathVariable Integer id) {

        usuarioService.deleteRol(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/roles/existe/{id}")
    public ResponseEntity<Boolean>
    existsRol(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                usuarioService.existsRolById(id));
    }
}
