package cl.tripplanner.turismo.reservas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TurismoReservasApplication {

    public static void main(String[] args) {
        SpringApplication.run(TurismoReservasApplication.class, args);
    }
}