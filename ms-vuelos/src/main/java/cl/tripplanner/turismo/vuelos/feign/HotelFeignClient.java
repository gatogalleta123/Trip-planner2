package cl.tripplanner.turismo.vuelos.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-hoteles")
public interface HotelFeignClient {

    @GetMapping("/api/hoteles/{id}")
    Object obtenerHotel(@PathVariable Integer id);

}