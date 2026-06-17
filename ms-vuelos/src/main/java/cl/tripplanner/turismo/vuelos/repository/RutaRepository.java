package cl.tripplanner.turismo.vuelos.repository;

import cl.tripplanner.turismo.vuelos.modelo.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RutaRepository extends JpaRepository<Ruta, Integer> {
    boolean existsByOrigenAndDestino(String origen, String destino);
}