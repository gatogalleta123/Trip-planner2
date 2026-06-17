package cl.tripplanner.turismo.reportes.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class HistorialReporteResponse {

    private Long id;

    private Long reporteId;

    private String reporteNombre;

    private LocalDateTime fechaEjecucion;

    private String estado;

    private String urlResultado;
}