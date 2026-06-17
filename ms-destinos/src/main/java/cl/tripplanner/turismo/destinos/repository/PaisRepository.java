package cl.tripplanner.turismo.destinos.repository;

import cl.tripplanner.turismo.destinos.modelo.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Integer> {

}