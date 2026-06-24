package cl.tripplanner.turismo.auth.controller;

import cl.tripplanner.turismo.auth.dto.RegisterRequest;
import cl.tripplanner.turismo.auth.dto.UpdateRequest;
import cl.tripplanner.turismo.auth.client.UsuarioClient;
import cl.tripplanner.turismo.auth.dto.AuthResponse;
import cl.tripplanner.turismo.auth.dto.LoginRequest;
import cl.tripplanner.turismo.auth.dto.UsuarioAuthResponse;
import cl.tripplanner.turismo.auth.dto.UsuarioInfoResponse;
import cl.tripplanner.turismo.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UsuarioClient usuarioClient;

    @GetMapping
    public List<UsuarioAuthResponse> findAll() {
        return authService.findAll();
    }

    @GetMapping("/{email}")
    public UsuarioAuthResponse findByEmail(@PathVariable String email) {
        return authService.findByEmail(email);
    }

    @PostMapping
    public UsuarioAuthResponse create(@Valid @RequestBody RegisterRequest request) {
        return authService.create(request);
    }

    @PutMapping("/{email}")
    public UsuarioAuthResponse update(@PathVariable String email,@Valid @RequestBody UpdateRequest request) {
        return authService.update(email, request);
    }

    @DeleteMapping("/{email}")
    public void deleteByEmail(@PathVariable String email) {
        authService.deleteByEmail(email);
    }

    //Feing

    @GetMapping("/usuarioInfo/{email}")
    public UsuarioInfoResponse findUsuarioInfo(@PathVariable String email) {
        return authService.findUsuarioInfoByEmail(email);
    }
    
    @GetMapping("/usuarioInfo")
    public List<UsuarioInfoResponse> findAllInfo() {
        return authService.findAllUsuarioInfo();
    }

    @GetMapping("/feingTest/{email}")
    public UsuarioAuthResponse test(@PathVariable String email) {
        return usuarioClient.findUsuario(email);
    }
    
    //@PostMapping("/login")
    //public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
    //    return ResponseEntity.ok(authService.login(request));
    //}

    @GetMapping("/exists/{email}")
    public boolean existsByEmail(
        @PathVariable String email) {

    return authService.existsByEmail(email);
    }
}