package cl.tripplanner.turismo.reservas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "ms-resennas", url = "http://localhost:9008")
public interface ResennaClient {

    @GetMapping("/resennas")
    List<Object> obtenerResennas();
}