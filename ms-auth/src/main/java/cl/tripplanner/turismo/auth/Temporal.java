package cl.tripplanner.turismo.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Temporal {

        public static void main(String[] args) {

        System.out.println(
            new BCryptPasswordEncoder()
                .encode("TripPlanner@2026")
        );
        }
}
