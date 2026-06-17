package cl.tripplanner.turismo.hoteles.repository;

import cl.tripplanner.turismo.hoteles.modelo.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CiudadRepository
        extends JpaRepository<Ciudad, Integer> {
}