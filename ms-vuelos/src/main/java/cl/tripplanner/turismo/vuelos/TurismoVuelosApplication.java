package cl.tripplanner.turismo.vuelos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TurismoVuelosApplication {

    public static void main(String[] args) {
        SpringApplication.run(TurismoVuelosApplication.class, args);
    }
}