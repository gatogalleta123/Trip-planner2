package cl.tripplanner.turismo.auth.client;


import cl.tripplanner.turismo.auth.dto.UsuarioAuthResponse;
import cl.tripplanner.turismo.auth.dto.UsuarioInfoResponse;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-usuarios")
public interface UsuarioClient {

    @GetMapping("/api/v1/usuarios/{email}")
    UsuarioAuthResponse findUsuario(@PathVariable String email);

    @GetMapping("/api/v1/usuarios/{email}")
    UsuarioInfoResponse findUsuarioInfo(@PathVariable String email);

    @GetMapping("/api/v1/usuarios")
    List<UsuarioAuthResponse> findAll();

    @GetMapping("/api/v1/usuarios")
    List<UsuarioInfoResponse> findAllInfo();

}