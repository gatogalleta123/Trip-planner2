package cl.tripplanner.turismo.reservas.repository;

import cl.tripplanner.turismo.reservas.modelo.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    List<Reserva> findByClienteId(Integer clienteId);

}