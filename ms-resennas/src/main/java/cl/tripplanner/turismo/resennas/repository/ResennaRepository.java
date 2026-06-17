package cl.tripplanner.turismo.resennas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.tripplanner.turismo.resennas.modelo.Resenna;

@Repository
public interface ResennaRepository extends JpaRepository<cl.tripplanner.turismo.resennas.modelo.Resenna, Integer> {

}