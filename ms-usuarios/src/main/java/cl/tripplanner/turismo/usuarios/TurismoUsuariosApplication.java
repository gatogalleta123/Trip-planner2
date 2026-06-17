package cl.tripplanner.turismo.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "cl.tripplanner.turismo.usuarios.client")
@SpringBootApplication
public class TurismoUsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(TurismoUsuariosApplication.class, args);
	}

}
