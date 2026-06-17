package cl.tripplanner.turismo.notificaciones.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.tripplanner.turismo.notificaciones.model.UsuarioProyeccion;

public interface UsuarioProyeccionRepository extends JpaRepository<UsuarioProyeccion, String> {
    Optional<UsuarioProyeccion> findByEmail(String email);
    void deleteByEmail(String email);
    List<UsuarioProyeccion> findAll();
}
