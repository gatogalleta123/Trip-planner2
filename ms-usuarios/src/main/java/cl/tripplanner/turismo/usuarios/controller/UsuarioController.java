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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/usuarios")
@Tag(
    name = "Usuarios",
    description = "API para la gestión de perfiles, roles y organizaciones"
)
public class UsuarioController {

    private final UsuarioService usuarioService;

    // ─────────────────────────────
    // USUARIOS
    // ─────────────────────────────

    @Operation(summary = "Obtener todos los usuarios")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @Operation(summary = "Obtener usuario por email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{email}")
    public ResponseEntity<UsuarioResponse> findByEmail(
            @Parameter(description = "Email del usuario") @PathVariable String email) {
        return ResponseEntity.ok(usuarioService.findByEmail(email));
    }

    @Operation(summary = "Crear usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<UsuarioResponse> create(
            @org.springframework.web.bind.annotation.RequestBody @Valid UsuarioRequest request) {

        UsuarioResponse creado = usuarioService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Actualizar usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{email}")
    public ResponseEntity<UsuarioResponse> update(
            @Parameter(description = "Email del usuario") @PathVariable String email,
            @org.springframework.web.bind.annotation.RequestBody @Valid UsuarioUpdateRequest request) {

        return ResponseEntity.ok(usuarioService.update(email, request));
    }

    @Operation(summary = "Eliminar usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "Email del usuario") @PathVariable String email) {

        usuarioService.deleteByEmail(email);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Verificar si existe un usuario por email")
    @GetMapping("/existe/email/{email}")
    public ResponseEntity<Boolean> existByEmail(
            @PathVariable String email) {

        return ResponseEntity.ok(usuarioService.existsByEmail(email));
    }

    // ─────────────────────────────
    // ORGANIZACIONES
    // ─────────────────────────────

    @Operation(summary = "Obtener todas las organizaciones")
    @GetMapping("/organizaciones")
    public ResponseEntity<List<OrganizacionResponse>> findAllOrganizaciones() {
        return ResponseEntity.ok(usuarioService.findAllOrganizaciones());
    }

    @Operation(summary = "Obtener organización por ID")
    @GetMapping("/organizaciones/{id}")
    public ResponseEntity<OrganizacionResponse> findOrganizacionById(
            @PathVariable Integer id) {

        return ResponseEntity.ok(usuarioService.findOrganizacionById(id));
    }

    @Operation(summary = "Crear organización")
    @PostMapping("/organizaciones")
    public ResponseEntity<OrganizacionResponse> createOrganizacion(
            @org.springframework.web.bind.annotation.RequestBody @Valid OrganizacionRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.createOrganizacion(request));
    }

    @Operation(summary = "Actualizar organización")
    @PutMapping("/organizaciones/{id}")
    public ResponseEntity<OrganizacionResponse> updateOrganizacion(
            @PathVariable Integer id,
            @org.springframework.web.bind.annotation.RequestBody @Valid OrganizacionRequest request) {

        return ResponseEntity.ok(usuarioService.updateOrganizacion(id, request));
    }

    @Operation(summary = "Eliminar organización")
    @DeleteMapping("/organizaciones/{id}")
    public ResponseEntity<Void> deleteOrganizacion(@PathVariable Integer id) {
        usuarioService.deleteOrganizacion(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Verificar existencia de organización")
    @GetMapping("/organizaciones/existe/{id}")
    public ResponseEntity<Boolean> existsOrganizacion(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.existsOrganizacionById(id));
    }

    // ─────────────────────────────
    // ROLES
    // ─────────────────────────────

    @Operation(summary = "Obtener todos los roles")
    @GetMapping("/roles")
    public ResponseEntity<List<RolResponse>> findAllRoles() {
        return ResponseEntity.ok(usuarioService.findAllRoles());
    }

    @Operation(summary = "Obtener rol por ID")
    @GetMapping("/roles/{id}")
    public ResponseEntity<RolResponse> findRolById(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.findRolById(id));
    }

    @Operation(summary = "Crear rol")
    @PostMapping("/roles")
    public ResponseEntity<RolResponse> createRol(
            @org.springframework.web.bind.annotation.RequestBody @Valid RolRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.createRol(request));
    }

    @Operation(summary = "Actualizar rol")
    @PutMapping("/roles/{id}")
    public ResponseEntity<RolResponse> updateRol(
            @PathVariable Integer id,
            @org.springframework.web.bind.annotation.RequestBody @Valid RolRequest request) {

        return ResponseEntity.ok(usuarioService.updateRol(id, request));
    }

    @Operation(summary = "Eliminar rol")
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Integer id) {
        usuarioService.deleteRol(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Verificar existencia de rol")
    @GetMapping("/roles/existe/{id}")
    public ResponseEntity<Boolean> existsRol(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.existsRolById(id));
    }
}