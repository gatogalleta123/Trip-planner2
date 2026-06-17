package cl.tripplanner.turismo.reservas.controller;

import cl.tripplanner.turismo.reservas.client.ResennaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    private final ResennaClient resennaClient;

    public TestController(ResennaClient resennaClient) {
        this.resennaClient = resennaClient;
    }

    @GetMapping("/test-resennas")
    public List<Object> obtenerResennas() {
        return resennaClient.obtenerResennas();
    }
}