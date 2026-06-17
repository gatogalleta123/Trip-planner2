package cl.tripplanner.turismo.reportes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.tripplanner.turismo.reportes.model.HistorialReporte;

public interface HistorialReporteRepository extends JpaRepository<HistorialReporte, Long> {

    List<HistorialReporte>
    findByEstado(String estado);

}