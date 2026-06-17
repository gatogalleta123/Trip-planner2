package cl.tripplanner.turismo.destinos.repository;

import cl.tripplanner.turismo.destinos.modelo.Atraccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtraccionRepository extends JpaRepository<Atraccion, Integer> {
}