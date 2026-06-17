package cl.tripplanner.turismo.reportes.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.tripplanner.common.exception.DuplicateResourceException;
import cl.tripplanner.common.exception.EntityNotFoundException;
import cl.tripplanner.common.exception.ReferentialIntegrityException;
import cl.tripplanner.turismo.reportes.dto.HistorialReporteResponse;
import cl.tripplanner.turismo.reportes.dto.ReporteRequest;
import cl.tripplanner.turismo.reportes.dto.ReporteResponse;
import cl.tripplanner.turismo.reportes.mapper.HistorialReporteMapper;
import cl.tripplanner.turismo.reportes.mapper.ReporteMapper;
import cl.tripplanner.turismo.reportes.model.Fuente;
import cl.tripplanner.turismo.reportes.model.HistorialReporte;
import cl.tripplanner.turismo.reportes.model.Reporte;
import cl.tripplanner.turismo.reportes.repository.FuenteRepository;
import cl.tripplanner.turismo.reportes.repository.HistorialReporteRepository;
import cl.tripplanner.turismo.reportes.repository.ReporteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReporteService {

    private final ReporteRepository reporteRepository;
    private final FuenteRepository fuenteRepository;
    private final HistorialReporteRepository historialRepository;

    private final ReporteMapper reporteMapper;
    private final HistorialReporteMapper historialMapper;

    public List<ReporteResponse> findAll() {
        return reporteMapper.toResponseList(
                reporteRepository.findAll()
        );
    }

    public ReporteResponse findById(long id) {
        return reporteMapper.toResponse(
                getReporteById(id)
        );
    }

    public List<ReporteResponse> findByActivo(Boolean activo) {
        return reporteMapper.toResponseList(
                reporteRepository.findByActivo(activo)
        );
    }

    @Transactional
    public ReporteResponse create(
            ReporteRequest request
    ) {

        validateReporteUnico(
                request.getNombre(),
                request.getFuenteId()
        );

        Fuente fuente = getFuenteById(
                request.getFuenteId()
        );

        Reporte reporte = new Reporte();

        reporteMapper.updateEntity(
                request,
                reporte
        );

        reporte.setFuente(fuente);

        return reporteMapper.toResponse(
                reporteRepository.save(reporte)
        );
    }

    @Transactional
    public ReporteResponse update(
            long id,
            ReporteRequest request
    ) {

        Reporte reporte = getReporteById(id);

        if (
                !reporte.getNombre().equalsIgnoreCase(
                        request.getNombre()
                )
        ) {

            validateReporteUnico(
                    request.getNombre(),
                    request.getFuenteId()
            );
        }

        Fuente fuente = getFuenteById(
                request.getFuenteId()
        );

        reporteMapper.updateEntity(
                request,
                reporte
        );

        reporte.setFuente(fuente);

        return reporteMapper.toResponse(
                reporteRepository.save(reporte)
        );
    }

    @Transactional
    public void deleteById(long id) {

        Reporte reporte = getReporteById(id);

        if (!reporte.getEjecuciones().isEmpty()) {

            throw new ReferentialIntegrityException(
                    "Reportes",
                    id,
                    "Historial de ejecuciones"
            );
        }

        reporteRepository.delete(reporte);
    }

    @Transactional
    public HistorialReporteResponse ejecutarReporte(
            long reporteId
    ) {

        Reporte reporte = getReporteById(
                reporteId
        );

        HistorialReporte historial =
                new HistorialReporte();

        historial.setReporte(reporte);

        historial.setFechaEjecucion(
                LocalDateTime.now()
        );

        historial.setEstado("generado");

        historial.setUrlResultado(
                generarUrlResultado(reporte)
        );

        return historialMapper.toResponse(
                historialRepository.save(historial)
        );
    }

    private Reporte getReporteById(long id) {

        return reporteRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Reportes",
                                "ID",
                                id
                        )
                );
    }

    private Fuente getFuenteById(long id) {

        return fuenteRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Fuentes",
                                "ID",
                                id
                        )
                );
    }

    private void validateReporteUnico(
            String nombre,
            Long fuenteId
    ) {

        reporteRepository
                .findByNombreAndFuenteId(
                        nombre,
                        fuenteId
                )
                .ifPresent(r -> {

                    throw new DuplicateResourceException(
                            "Un reporte",
                            "Nombre",
                            nombre,
                            r.getFuente().getNombre()
                    );
                });
    }

    private String generarUrlResultado(
            Reporte reporte
    ) {

        return "http://files/reportes/"
                + reporte.getNombre()
                .replace(" ", "_")
                .toLowerCase()
                + "."
                + reporte.getFormato();
    }
}