package cl.tripplanner.turismo.reportes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.tripplanner.turismo.reportes.model.Reporte;

public interface ReporteRepository extends JpaRepository<Reporte, Long> {

    List<Reporte> findByActivo(Boolean activo);

    Optional<Reporte> findByNombreAndFuenteId(String nombre, Long fuenteId);

}
