package cl.tripplanner.turismo.hoteles.repository;

import cl.tripplanner.turismo.hoteles.modelo.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitacionRepository
        extends JpaRepository<Habitacion, Integer> {
}