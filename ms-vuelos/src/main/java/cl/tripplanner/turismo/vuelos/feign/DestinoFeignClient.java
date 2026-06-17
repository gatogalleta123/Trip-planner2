package cl.tripplanner.turismo.vuelos.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-destinos")
public interface DestinoFeignClient {

    @GetMapping("/api/destinos/{id}")
    Object obtenerDestino(@PathVariable Integer id);

}