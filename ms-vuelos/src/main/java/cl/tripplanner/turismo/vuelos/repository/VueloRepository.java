package cl.tripplanner.turismo.vuelos.repository;

import cl.tripplanner.turismo.vuelos.modelo.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VueloRepository extends JpaRepository<Vuelo, Integer> {
}