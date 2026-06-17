package cl.tripplanner.turismo.reportes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.tripplanner.turismo.reportes.model.ReservaProyeccion;

public interface ReservaProyeccionRepository extends JpaRepository<ReservaProyeccion, String> {
    Optional<ReservaProyeccion> findById(String id);
    void deleteById(String id);
    List<ReservaProyeccion> findAll();
}
