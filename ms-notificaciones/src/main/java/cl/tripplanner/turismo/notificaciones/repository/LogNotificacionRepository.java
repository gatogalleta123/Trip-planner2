package cl.tripplanner.turismo.notificaciones.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.tripplanner.turismo.notificaciones.model.LogNotificacion;

public interface LogNotificacionRepository extends JpaRepository<LogNotificacion, Long> {
    List<LogNotificacion> findByNotificacionCodigo(String codigo);
}
