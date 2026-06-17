package cl.tripplanner.turismo.pagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.tripplanner.turismo.pagos.modelo.Transaccion;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Integer> {

}