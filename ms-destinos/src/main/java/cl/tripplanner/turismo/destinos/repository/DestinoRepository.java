package cl.tripplanner.turismo.destinos.repository;

import cl.tripplanner.turismo.destinos.modelo.Destino;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinoRepository extends JpaRepository<Destino, Integer> {
}