package cl.tripplanner.turismo.usuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.tripplanner.turismo.usuarios.dto.UsuarioAuthResponse;

@FeignClient(name = "ms-auth")
public interface AuthClient {

    @GetMapping("/api/v1/auth/exists/{email}")
    boolean existsByEmail(@PathVariable String email);

}
