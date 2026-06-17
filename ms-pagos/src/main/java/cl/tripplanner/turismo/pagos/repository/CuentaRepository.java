package cl.tripplanner.turismo.pagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.tripplanner.turismo.pagos.modelo.Cuenta;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {

}