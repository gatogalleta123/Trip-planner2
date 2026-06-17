package cl.tripplanner.turismo.notificaciones.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.tripplanner.turismo.notificaciones.model.Notificacion;

public interface NotificacionRepository extends JpaRepository<Notificacion, String> {
    Optional<Notificacion> findByCodigo(String codigo);

    List<Notificacion> findByEnviada(Boolean enviada);

    List<Notificacion> findByCanal(String canal);
}
