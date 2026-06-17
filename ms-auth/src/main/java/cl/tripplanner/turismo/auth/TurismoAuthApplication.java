package cl.tripplanner.turismo.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "cl.tripplanner.turismo.auth.client")
@SpringBootApplication
public class TurismoAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(TurismoAuthApplication.class, args);
	}

}
