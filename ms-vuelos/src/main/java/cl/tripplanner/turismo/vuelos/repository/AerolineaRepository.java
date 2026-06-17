package cl.tripplanner.turismo.vuelos.repository;

import cl.tripplanner.turismo.vuelos.modelo.Aerolinea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AerolineaRepository extends JpaRepository<Aerolinea, Integer> {
}